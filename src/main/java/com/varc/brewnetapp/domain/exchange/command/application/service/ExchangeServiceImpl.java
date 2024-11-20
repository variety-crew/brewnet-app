package com.varc.brewnetapp.domain.exchange.command.application.service;

import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.domain.exchange.command.application.repository.*;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity.*;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeApproveReqVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqItemVO;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.vo.ExchangeReqVO;
import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.exchange.ExchangeStatus;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.exception.ExchangeNotFoundException;
import com.varc.brewnetapp.exception.InvalidStatusException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("ExchangeServiceCommand")
@RequiredArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements ExchangeService{

    // 이후 의존성 수정 필요
    private final ExchangeRepository exchangeRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ExchangeItemRepository exchangeItemRepository;
    private final ExchangeStatusHistoryRepository exchangeStatusHistoryRepository;
    private final com.varc.brewnetapp.domain.exchange.query.service.ExchangeServiceImpl exchangeServiceQuery;
    private final ExchangeApproverRepository exchangeApproverRepository;

    @Override
    @Transactional
    public void createExchange(String loginId, ExchangeReqVO exchangeReqVO) {

        // 1. 해당 주문이 이 가맹점의 주문이 맞는지 확인
        ExOrder order = orderRepository.findById(exchangeReqVO.getOrderCode()).orElse(null);

        // 2. 교환 객체 생성
        if (exchangeServiceQuery.isValidOrderByFranchise(loginId, exchangeReqVO.getOrderCode())) {
            Exchange exchange = Exchange.builder()
                    .comment(null)
                    .createdAt(String.valueOf(LocalDateTime.now()))
                    .active(true)
                    .reason(exchangeReqVO.getReason())
                    .explanation(exchangeReqVO.getExplanation())
                    .approvalStatus(Approval.UNCONFIRMED)
                    .order(order)
                    .memberCode(null)
                    .delivery(null)
                    .drafterApproved(DrafterApproved.NONE)
                    .sumPrice(exchangeReqVO.getSumPrice())
                    .build();

            // 3. 교환별상품 저장
            int exchangeCode = exchange.getExchangeCode();

            for (ExchangeReqItemVO reqItem: exchangeReqVO.getExchangeItemList()) {

                // 복합키 객체 생성
                ExchangeItemCode exchangeItemCode = new ExchangeItemCode();
                exchangeItemCode.setExchangeCode(exchangeCode);                 // 교환 코드 설정
                exchangeItemCode.setItemCode(reqItem.getItemCode());            // 상품 코드 설정

                // ExchangeItem 객체 생성
                ExchangeItem exchangeItem = new ExchangeItem();
                exchangeItem.setExchangeItemCode(exchangeItemCode);  // 복합키 설정
                exchangeItem.setQuantity(reqItem.getQuantity());

                exchangeItemRepository.save(exchangeItem);
            }


            // 4. 교환상태이력 저장
            ExchangeStatusHistory exchangeStatusHistory = ExchangeStatusHistory.builder()
                    .status(ExchangeStatus.REQUESTED)
                    .createdAt(String.valueOf(LocalDateTime.now()))
                    .active(true)
                    .exchange(exchange)
                    .build();

            exchangeStatusHistoryRepository.save(exchangeStatusHistory);


            // 5. 교환품목사진 저장

        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 주문에 대해서만 교환 요청할 수 있습니다");
        }
    }

    @Override
    @Transactional
    public boolean cancelExchange(String loginId, Integer exchangeCode) {

        // 1. 해당 취소요청의 교환내역이 이 가맹점에서 작성한 것이 맞는지 확인
        if (exchangeServiceQuery.isValidExchangeByFranchise(loginId, exchangeCode)) {

            // 2. 교환 상태 이력(tbl_exchange_status_history)테이블에 취소내역 저장
            Exchange exchange = exchangeRepository.findById(exchangeCode)
                    .orElseThrow(() -> new ExchangeNotFoundException("교환 코드가 존재하지 않습니다."));

            ExchangeStatus exchangeStatus = exchangeServiceQuery.findExchangeLatestStatus(exchangeCode);

            // status가 REQUESTED인 경우에만 취소 가능
            if (exchangeStatus == ExchangeStatus.REQUESTED) {

                ExchangeStatusHistory exchangeStatusHistory = ExchangeStatusHistory.builder()
                        .status(ExchangeStatus.CANCELED)
                        .createdAt(String.valueOf(LocalDateTime.now()))
                        .active(true)
                        .exchange(exchange)
                        .build();

                exchangeStatusHistoryRepository.save(exchangeStatusHistory);

                // 3. 교환(tbl_exchange) 테이블의 활성화(active)를 false로 변경
                exchange = exchange.toBuilder()     // 새 객체가 생성되지만, 영속성 컨텍스트는 아님
                        .active(false)
                        .build();
                exchangeRepository.save(exchange);  //객체가 영속성 컨텍스트에 저장되고, 엔티티 매니저가 이 객체를 관리함

                return true;
            } else {
                throw new InvalidStatusException("교환신청 취소가 불가능합니다. 교환상태가 '교환요청'인 경우에만 취소할 수 있습니다");
            }
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 교환요청에 대해서만 취소할 수 있습니다");
        }
    }

    @Override
    @Transactional
    public void approveExchange(String loginId, ExchangeApproveReqVO exchangeApproveReqVO) {
        /*
         * 교환 결재 신청(최초기안자)
         * - 결재 신청이 가능한 조건:
         *   1. 교환 결재 상태(approved) == UNCONFIRMED
         *   2. 기안자의 교환 승인 여부(drafter_approved) == NONE
         *   3. 교환 기안자(member_code) == null
        * */


        // 1. 교한 결재 신청이 가능한지 확인
        Exchange exchange = exchangeRepository.findById(exchangeApproveReqVO.getExchangeCode())
                .orElseThrow(() -> new ExchangeNotFoundException("교환 코드가 존재하지 않습니다."));

        if (exchange.getApprovalStatus() != Approval.UNCONFIRMED) {
            throw new InvalidStatusException("이미 결재신청이 완료된 교환입니다.");
        } else if (exchange.getDrafterApproved() != DrafterApproved.NONE) {
            throw new InvalidStatusException("이미 결재신청이 진행 중인 교환입니다.");
        } else if (exchange.getMemberCode() != null) {
            throw new InvalidStatusException("이미 결재신청이 진행 중인 교환입니다.");
        }


        // 2. 교환(tbl_exchange) 테이블 '기안자의 교환 승인 여부(drafter_approved)' 변경
        //    '교환 결재 상태(approved)'는 계속 UNCONFIRMED (최종결재자가 결재완료시 변경됨)

        Member member = memberRepository.findById(loginId)
                .orElseThrow(() -> new MemberNotFoundException("해당 아이디의 회원이 존재하지 않습니다."));

        exchange = exchange.toBuilder()
                .drafterApproved(exchangeApproveReqVO.getApproval())
                .memberCode(member)
                .comment(exchangeApproveReqVO.getComment())
                .build();


        exchangeRepository.save(exchange);


        // 3. 교환 별 결재자들(tbl_exchange_approver) 등록 (기안자 제외 approved=UNCONFIRMED)

        // 2-1. 기안자 등록 -> 여기에 기안자도 등록되는거 맞는지 확인 필요
        // 복합키 객체 생성
        ExchangeApproverCode drafterApproverCode = ExchangeApproverCode.builder()
                .memberCode(member.getMemberCode())         // 멤버 코드 설정
                .exchangeCode(exchange.getExchangeCode())   // 교환 코드 설정
                .build();

        // ExchangeApprover 객체 생성
        ExchangeApprover drafterApprover = ExchangeApprover.builder()
                .exchangeApproverCode(drafterApproverCode) // 복합키 설정
                .approved(Approval.APPROVED) // 기안자는 승인으로 저장?
                .createdAt(String.valueOf(LocalDateTime.now()))
                .comment(exchange.getComment())
                .active(true)
                .build();

        exchangeApproverRepository.save(drafterApprover);


        // 2-2. 결재자들 등록
        for (Integer approverCode : exchangeApproveReqVO.getApproverCodeList()) {

            // 복합키 객체 생성
            ExchangeApproverCode exchangeApproverCode = ExchangeApproverCode.builder()
                    .memberCode(member.getMemberCode())         // 멤버 코드 설정
                    .exchangeCode(exchange.getExchangeCode())   // 교환 코드 설정
                    .build();

            // ExchangeApprover 객체 생성
            ExchangeApprover exchangeApprover = ExchangeApprover.builder()
                    .exchangeApproverCode(exchangeApproverCode)  // 복합키 설정
                    .approved(Approval.UNCONFIRMED)
                    .createdAt(null)
                    .comment(null)
                    .active(true)
                    .build();

            exchangeApproverRepository.save(exchangeApprover);
        }
    }
}
