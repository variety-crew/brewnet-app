package com.varc.brewnetapp.domain.returning.command.application.service;

import com.varc.brewnetapp.common.S3ImageService;
import com.varc.brewnetapp.common.domain.approve.Approval;
import com.varc.brewnetapp.common.domain.drafter.DrafterApproved;
import com.varc.brewnetapp.common.domain.order.Available;
import com.varc.brewnetapp.common.domain.returning.ReturningStatus;
import com.varc.brewnetapp.domain.exchange.command.application.repository.ExOrderItemRepository;
import com.varc.brewnetapp.domain.exchange.command.application.repository.ExOrderRepository;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrder;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrderItem;
import com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity.ExOrderItemCode;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningImg;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningItem;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.ReturningStatusHistory;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.Returning;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity.compositionkey.ReturningItemCode;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningReqItemVO;
import com.varc.brewnetapp.domain.returning.command.domain.aggregate.vo.ReturningReqVO;
import com.varc.brewnetapp.domain.returning.command.domain.repository.ReturningImgRepository;
import com.varc.brewnetapp.domain.returning.command.domain.repository.ReturningItemRepository;
import com.varc.brewnetapp.domain.returning.command.domain.repository.ReturningStatusHistoryRepository;
import com.varc.brewnetapp.domain.returning.command.domain.repository.ReturningRepository;
import com.varc.brewnetapp.exception.ExchangeNotFoundException;
import com.varc.brewnetapp.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Service("ReturningServiceCommand")
@RequiredArgsConstructor
@Slf4j
public class ReturningServiceImpl implements ReturningService{

    private final ReturningRepository returningRepository;
    private final com.varc.brewnetapp.domain.returning.query.service.ReturningServiceImpl returningServiceQuery;
    private final ExOrderRepository exOrderRepository;  // 임시
    private final ExOrderItemRepository exOrderItemRepository; // 임시
    private final ReturningItemRepository returningItemRepository;
    private final ReturningStatusHistoryRepository returningStatusHistoryRepository;
    private final ReturningImgRepository returningImgRepository;
    private final S3ImageService s3ImageService;


    @Override
    @Transactional
    public Integer franCreateReturning(String loginId, ReturningReqVO returningReqVO, List<MultipartFile> returningImageList) {
        /*TODO.
         * 반품 신청 시 변하는 상태값
         * [1] 반품 결재 상태           - tbl_return : approvalStatus = UNCONFIRMED
         * [2] 기안자의 반품 승인 여부    - tbl_return : drafterApproved = NONE
         * [3] 반품상태                - tbl_return_status_history : status = REQUESTED
         * [4] 반품/교환요청 가능여부     - tbl_order_item : available = UNAVAILABLE
         * */

        ExOrder order = exOrderRepository.findById(returningReqVO.getOrderCode()).orElse(null);

        if (returningServiceQuery.isValidOrderByFranchise(loginId, returningReqVO.getOrderCode())) {

            // 2. 반품 객체 생성
            Returning returning = Returning.builder()
                    .comment(null)
                    .createdAt(String.valueOf(LocalDateTime.now()))
                    .active(true)
                    .reason(returningReqVO.getReason())
                    .explanation(returningReqVO.getExplanation())
                    .approvalStatus(Approval.UNCONFIRMED)   // [1] 교환 결재 상태
                    .order(order)
                    .memberCode(null)
                    .delivery(null)
                    .drafterApproved(DrafterApproved.NONE)  // [2] 기안자의 교환 승인 여부
                    .sumPrice(returningReqVO.getSumPrice())
                    .build();

            returningRepository.save(returning);


            for (ReturningReqItemVO reqItem : returningReqVO.getReturningItemList()) {
                // 3. 주문 별 상품 - 반품/교환요청 가능여부 변경
                // 복합키 객체 생성
                ExOrderItemCode exOrderItemCode = ExOrderItemCode.builder()
                        .orderCode(order.getOrderCode())            // 주문 코드 설정
                        .itemCode(reqItem.getItemCode())            // 상품 코드 설정
                        .build();

                ExOrderItem exOrderItem = exOrderItemRepository.findByOrderItemCode(exOrderItemCode)
                        .orElseThrow(() -> new ExchangeNotFoundException("존재하지 않는 주문 상품이 포함되어 있습니다."));
                if (exOrderItem.getAvailable() != Available.AVAILABLE) {
                    throw new IllegalArgumentException("반품 신청이 불가한 주문 상품이 포함되어 있습니다.");
                }

                exOrderItem = exOrderItem.toBuilder()
                        .available(Available.UNAVAILABLE)        // [4] 반품/교환요청 가능여부
                        .build();

                exOrderItemRepository.save(exOrderItem);

                // 4. 교환별상품 저장
                // 복합키 객체 생성
                ReturningItemCode returningItemCode = ReturningItemCode.builder()
                        .returningCode(returning.getReturningCode())    // 교환 코드 설정
                        .itemCode(reqItem.getItemCode())                // 상품 코드 설정
                        .build();

                // ExchangeItem 객체 생성
                ReturningItem returningItem = ReturningItem.builder()
                        .returningItemCode(returningItemCode)           // 복합키 설정
                        .quantity(exOrderItem.getQuantity())            // 기존 주문 별 상품의 수량 그대로 저장
                        .build();

                returningItemRepository.save(returningItem);

            }

            // 5. 교환상태이력 저장
            saveReturningStatusHistory(ReturningStatus.REQUESTED, returning);

            // 6. 교환품목사진 저장
            for (MultipartFile image : returningImageList) {
                String s3Url = s3ImageService.upload(image);
                ReturningImg returningImg = ReturningImg.builder()
                        .imageUrl(s3Url)
                        .returning(returning)
                        .build();

                returningImgRepository.save(returningImg);
            }


            // 7. 교환코드(exchangeCode) 리턴 - 프론트엔드 상세페이지 이동 위해
            return returning.getReturningCode();

        } else {
            throw new UnauthorizedAccessException("로그인한 가맹점에서 작성한 주문에 대해서만 반품 요청할 수 있습니다");
        }
    }

    private void saveReturningStatusHistory(ReturningStatus status, Returning returning) {
        ReturningStatusHistory returnStatusHistory = ReturningStatusHistory.builder()
                .status(status)   // [3] 반품 상태 이력
                .createdAt(String.valueOf(LocalDateTime.now()))
                .active(true)
                .returning(returning)
                .build();
        returningStatusHistoryRepository.save(returnStatusHistory);
    }

}
