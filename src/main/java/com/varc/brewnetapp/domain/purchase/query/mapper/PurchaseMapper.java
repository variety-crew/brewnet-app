package com.varc.brewnetapp.domain.purchase.query.mapper;

import com.varc.brewnetapp.domain.purchase.common.KindOfApproval;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseCriteria;
import com.varc.brewnetapp.domain.purchase.common.SearchPurchaseItemCriteria;
import com.varc.brewnetapp.domain.purchase.query.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    List<LetterOfPurchaseDTO> searchLettersOfPurchase(SearchPurchaseCriteria criteria);

    int getTotalPurchaseCount(SearchPurchaseCriteria criteria);

    LetterOfPurchaseDetailDTO selectLetterOfPurchaseByPurchaseCode(int letterOfPurchaseCode);

    PurchaseApprovalLineDTO selectApprovalLineByPurchaseCode(int letterOfPurchaseCode);

    List<ApprovedPurchaseItemDTO> selectApprovedPurchaseItemTotal(SearchPurchaseItemCriteria criteria);

    int getTotalApprovedPurchaseItemCount(SearchPurchaseItemCriteria criteria);

    List<ApprovedPurchaseItemDTO> selectApprovedPurchaseItemUncheck(SearchPurchaseItemCriteria criteria);

    int getApprovedPurchaseItemUncheckCount(SearchPurchaseItemCriteria criteria);

    List<PurchaseApproverMemberDTO> selectApproversByKind(KindOfApproval approvalLine);
}
