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

        // 1. 교환 저장
        Exchange exchange = new Exchange();

        // 1-1. 해당 주문이 이 가맹점의 주문이 맞는지 확인
        ExOrder order = orderRepository.findById(exchangeReqVO.getOrderCode()).orElse(null);

        if (exchangeServiceQuery.isValidOrderByFranchise(loginId, exchangeReqVO.getOrderCode())) {
            exchange.setComment(null);          // 첨언 null
            exchange.setCreatedAt(String.valueOf(LocalDateTime.now()));
            exchange.setActive(true);
            exchange.setReason(exchangeReqVO.getReason());
            exchange.setExplanation(exchangeReqVO.getExplanation());
            exchange.setApproved(Approval.UNCONFIRMED);
            exchange.setOrder(order);
            exchange.setMemberCode(null);       // 교환 기안자 null
            exchange.setDelivery(null);         // 배송기사 null
            exchange.setDrafterApproved(DrafterApproved.NONE);
            exchange.setSumPrice(exchangeReqVO.getSumPrice());

            exchangeRepository.save(exchange);


            // 2. 교환별상품 저장
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


            // 3. 교환상태이력 저장
            ExchangeStatusHistory exchangeStatusHistory = new ExchangeStatusHistory();
            exchangeStatusHistory.setStatus(ExchangeStatus.REQUESTED);
            exchangeStatusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
            exchangeStatusHistory.setActive(true);
            exchangeStatusHistory.setExchange(exchange);

            exchangeStatusHistoryRepository.save(exchangeStatusHistory);


            // 4. 교환품목사진 저장

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

                ExchangeStatusHistory exchangeStatusHistory = new ExchangeStatusHistory();
                exchangeStatusHistory.setStatus(ExchangeStatus.CANCELED);
                exchangeStatusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
                exchangeStatusHistory.setActive(true);
                exchangeStatusHistory.setExchange(exchange);

                exchangeStatusHistoryRepository.save(exchangeStatusHistory);

                // 3. 교환(tbl_exchange) 테이블의 활성화(active)를 false로 변경
                exchange.setActive(false);

                return true;
            } else {
                throw new InvalidStatusException("교환신청 취소가 불가능합니다. 교환상태가 '교환요청'인 경우에만 취소할 수 있습니다");
            }
        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 교환요청에 대해서만 취소할 수 있습니다");
        }
    }

    @Override
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

        if (exchange.getApproved() != Approval.UNCONFIRMED) {
            throw new InvalidStatusException("이미 결재신청이 완료된 교환입니다.");
        } else if (exchange.getDrafterApproved() != DrafterApproved.NONE) {
            throw new InvalidStatusException("이미 결재신청이 진행 중인 교환입니다.");
        } else if (exchange.getMemberCode() != null) {
            throw new InvalidStatusException("이미 결재신청이 진행 중인 교환입니다.");
        }


        // 2. 교환(tbl_exchange) 테이블 '기안자의 교환 승인 여부(drafter_approved)' 변경
        //    '교환 결재 상태(approved)'는 계속 UNCONFIRMED (최종결재자가 결재완료시 변경됨)
        exchange.setDrafterApproved(exchangeApproveReqVO.getApproval());

        Member member = memberRepository.findById(loginId)
                .orElseThrow(() -> new MemberNotFoundException("해당 아이디의 회원이 존재하지 않습니다."));
        exchange.setMemberCode(member);

        exchange.setComment(exchangeApproveReqVO.getComment());

        exchangeRepository.save(exchange);
        log.info("*** ExchangeServiceCommand approveExchange - exchange: {}", exchange);


        // 3. 교환 별 결재자들(tbl_exchange_approver) 등록 (기안자 제외 approved=UNCONFIRMED)

        // 2-1. 기안자 등록 -> 여기에 기안자도 등록되는거 맞는지 확인 필요
        // 복합키 객체 생성
        ExchangeApproverCode drafterApproverCode = new ExchangeApproverCode();
        drafterApproverCode.setMemberCode(member.getMemberCode());                     // 멤버 코드 설정
        drafterApproverCode.setExchangeCode(exchangeApproveReqVO.getExchangeCode());   // 교환 코드 설정

        // ExchangeApprover 객체 생성
        ExchangeApprover drafterApprover = new ExchangeApprover();
        drafterApprover.setExchangeApproverCode(drafterApproverCode);  // 복합키 설정
        drafterApprover.setApproved(Approval.APPROVED); // 기안자는 승인으로 저장?
        drafterApprover.setCreatedAt(String.valueOf(LocalDateTime.now()));
        drafterApprover.setComment(exchange.getComment());
        drafterApprover.setActive(true);

        exchangeApproverRepository.save(drafterApprover);

        // 2-2. 결재자들 등록
        for (Integer approverCode : exchangeApproveReqVO.getApproverCodeList()) {

            // 복합키 객체 생성
            ExchangeApproverCode exchangeApproverCode = new ExchangeApproverCode();
            exchangeApproverCode.setMemberCode(approverCode);                                 // 멤버 코드 설정
            exchangeApproverCode.setExchangeCode(exchangeApproveReqVO.getExchangeCode());     // 교환 코드 설정

            // ExchangeApprover 객체 생성
            ExchangeApprover exchangeApprover = new ExchangeApprover();
            exchangeApprover.setExchangeApproverCode(exchangeApproverCode);  // 복합키 설정
            exchangeApprover.setApproved(Approval.UNCONFIRMED);
            exchangeApprover.setCreatedAt(null);
            exchangeApprover.setComment(null);
            exchangeApprover.setActive(true);

            exchangeApproverRepository.save(exchangeApprover);
        }
    }
}
