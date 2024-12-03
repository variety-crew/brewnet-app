package com.varc.brewnetapp.domain.item.command.application.service;

import com.varc.brewnetapp.common.S3ImageService;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.Correspondent;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.CorrespondentItem;
import com.varc.brewnetapp.domain.correspondent.command.domain.aggregate.CorrespondentItemId;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentItemRepository;
import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentRepository;
import com.varc.brewnetapp.domain.item.command.application.dto.CreateItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.DeleteItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.MustBuyItemDTO;
import com.varc.brewnetapp.domain.item.command.application.dto.UpdateItemRequestDTO;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.Item;
import com.varc.brewnetapp.domain.item.command.domain.aggregate.entity.MandatoryPurchase;
import com.varc.brewnetapp.domain.item.command.domain.repository.ItemRepository;
import com.varc.brewnetapp.domain.item.command.domain.repository.MandatoryPurchaseRepository;
import com.varc.brewnetapp.domain.notice.command.domain.aggregate.entity.NoticeImage;
import com.varc.brewnetapp.exception.DuplicateException;
import com.varc.brewnetapp.exception.EmptyDataException;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.InvalidItemException;
import com.varc.brewnetapp.exception.ItemNotFoundException;
import com.varc.brewnetapp.exception.MandatoryPurchaseNotFound;
import com.varc.brewnetapp.exception.MustBuyItemAlreadySet;
import com.varc.brewnetapp.utility.time.Formatter;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final MandatoryPurchaseRepository mandatoryPurchaseRepository;
    private final S3ImageService s3ImageService;
    private final CorrespondentRepository correspondentRepository;
    private final CorrespondentItemRepository correspondentItemRepository;

    @Autowired
    public ItemServiceImpl(
            ItemRepository itemRepository,
            MandatoryPurchaseRepository mandatoryPurchaseRepository,
            S3ImageService s3ImageService,
            CorrespondentRepository correspondentRepository,
            CorrespondentItemRepository correspondentItemRepository
    ) {
        this.itemRepository = itemRepository;
        this.mandatoryPurchaseRepository = mandatoryPurchaseRepository;
        this.s3ImageService = s3ImageService;
        this.correspondentRepository = correspondentRepository;
        this.correspondentItemRepository = correspondentItemRepository;
    }

    @Override
    public void setMustBuyItem(
            int memberCode,
            Integer itemCode,
            MustBuyItemDTO mustBuyItemDTO
    ) {
        itemRepository.findItemByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new InvalidItemException("유효하지 않은 품목입니다."));

        if (mandatoryPurchaseRepository.findMandatoryPurchaseByItemCodeAndActiveTrue(itemCode).isPresent()) {
            throw new MustBuyItemAlreadySet("이미 해당 품목에 대한 필수 구매 정보가 지정되어있습니다.");
        } else {
            mandatoryPurchaseRepository.save(
                    MandatoryPurchase.builder()
                            .minQuantity(mustBuyItemDTO.getQuantity())
                            .createdAt(LocalDateTime.now())
                            .active(true)
                            .itemCode(itemCode)
                            .dueDate(Formatter.toLocalDateTime(mustBuyItemDTO.getDueDate()))
                            .memberCode(memberCode)
                            .build()
            );
        }
    }

    @Override
    @Transactional
    public void updateMustByItem(
            int memberCode,
            int itemCode,
            MustBuyItemDTO mustBuyItemDTO
    ) {
        itemRepository.findItemByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new InvalidItemException("유효하지 않은 품목입니다."));
        MandatoryPurchase targetMandatoryPurchase = mandatoryPurchaseRepository.findMandatoryPurchaseByItemCodeAndActiveTrue(itemCode)
                .orElseThrow(() -> new MandatoryPurchaseNotFound("해당 품목에 대한 필수 구매 설정이 되어있지 않습니다."));

        mandatoryPurchaseRepository.save(
                MandatoryPurchase.builder()
                        .mandatoryPurchaseCode(targetMandatoryPurchase.getMandatoryPurchaseCode())
                        .minQuantity(mustBuyItemDTO.getQuantity())
                        .createdAt(LocalDateTime.now())
                        .active(targetMandatoryPurchase.isActive())
                        .itemCode(itemCode)
                        .dueDate(Formatter.toLocalDateTime(mustBuyItemDTO.getDueDate()))
                        .memberCode(memberCode)
                        .build()
        );
    }

    @Override
    @Transactional
    public void createItem(CreateItemRequestDTO createItemRequestDTO, MultipartFile image) {

        if(createItemRequestDTO.getItemUniqueCode() == null)
            createItemRequestDTO.setItemUniqueCode(String.valueOf(UUID.randomUUID()));

        String s3Url = null;

        if (image != null){
            try {
                s3Url = s3ImageService.upload(image);
            }catch (InvalidDataException e) {
                throw new InvalidDataException("이미지를 저장할 수 없습니다");
            }

            if(s3Url == null)
                throw new InvalidDataException("이미지가 저장되지 않았습니다");
        }

        Item item = Item.builder()
            .name(createItemRequestDTO.getName())
            .purchasePrice(createItemRequestDTO.getPurchasePrice())
            .sellingPrice(createItemRequestDTO.getSellingPrice())
            .imageUrl(s3Url)
            .safetyStock(createItemRequestDTO.getSafetyStock())
            .itemUniqueCode(createItemRequestDTO.getItemUniqueCode())
            .categoryCode(createItemRequestDTO.getSubCategoryCode())
            .active(true)
            .createdAt(LocalDateTime.now())
            .build();

        Item createItem = itemRepository.save(item);

        if(createItemRequestDTO.getCorrespondentCode() != null){
            Correspondent correspondent = correspondentRepository.findById(
                createItemRequestDTO.getCorrespondentCode())
                .orElseThrow(() -> new EmptyDataException("입력하신 가맹점 코드와 일치하는 가맹점 정보가 없습니다"));

            CorrespondentItemId correspondentItemId = new CorrespondentItemId(correspondent.getCorrespondentCode(),
                createItem.getItemCode());

            CorrespondentItem correspondentItem = correspondentItemRepository.findById(correspondentItemId)
                .orElse(null);

            if(correspondentItem != null)
                throw new InvalidDataException("입력하신 정보의 상품이 이미 가맹점 별 상품으로 존재합니다");

            CorrespondentItem newCorrespondentItem = new CorrespondentItem(
                correspondent.getCorrespondentCode(),
                createItem.getItemCode(),
                LocalDateTime.now(),
                true
            );

            correspondentItemRepository.save(newCorrespondentItem);
        }

    }

    @Override
    @Transactional
    public void updateItem(UpdateItemRequestDTO updateItemRequestDTO, MultipartFile image) {

        Item existItem = itemRepository.findById(updateItemRequestDTO.getItemCode()).orElseThrow(
            () -> new EmptyDataException("수정하려는 상품의 코드를 잘못 입력하였습니다")
        );

        if(updateItemRequestDTO.getSubCategoryCode() != null)
            existItem.setCategoryCode(updateItemRequestDTO.getSubCategoryCode());

        if(updateItemRequestDTO.getItemUniqueCode() != null)
            existItem.setItemUniqueCode(updateItemRequestDTO.getItemUniqueCode());

        if(updateItemRequestDTO.getName() != null)
            existItem.setName(updateItemRequestDTO.getName());

        if(updateItemRequestDTO.getPurchasePrice() != null)
            existItem.setPurchasePrice(updateItemRequestDTO.getPurchasePrice());

        if(updateItemRequestDTO.getSellingPrice() != null)
            existItem.setSellingPrice(updateItemRequestDTO.getSellingPrice());

        if(updateItemRequestDTO.getSafetyStock() != null)
            existItem.setSafetyStock(updateItemRequestDTO.getSafetyStock());

        if(updateItemRequestDTO.getActive() != null)
            existItem.setActive(updateItemRequestDTO.getActive());

        String s3Url = null;

        if (image != null){
            try {
                s3Url = s3ImageService.upload(image);
            }catch (InvalidDataException e) {
                throw new InvalidDataException("이미지를 저장할 수 없습니다");
            }

            if(s3Url == null)
                throw new InvalidDataException("이미지가 저장되지 않았습니다");

            existItem.setImageUrl(s3Url);
        }

        if(updateItemRequestDTO.getCorrespondentCode() != null){

            List<CorrespondentItem> existCorrespondentItemList = correspondentItemRepository
                .findByItemCodeAndActiveTrue(updateItemRequestDTO.getItemCode());

            if(existCorrespondentItemList.size() > 0 && !existCorrespondentItemList.isEmpty()){
                for (CorrespondentItem correspondentItem : existCorrespondentItemList){
                    correspondentItem.setActive(false);
                    correspondentItemRepository.save(correspondentItem);
                }
            }

            CorrespondentItemId existCorrespondentItemId = new CorrespondentItemId(
                updateItemRequestDTO.getCorrespondentCode(),
                existItem.getItemCode());

            CorrespondentItem existCorrespondentItem = correspondentItemRepository.findById(existCorrespondentItemId)
                .orElse(null);

            if(existCorrespondentItem != null)
                correspondentItemRepository.delete(existCorrespondentItem);

            CorrespondentItem newCorrespondentItem = new CorrespondentItem(
                updateItemRequestDTO.getCorrespondentCode(),
                existItem.getItemCode(),
                LocalDateTime.now(),
                true
            );

            correspondentItemRepository.save(newCorrespondentItem);

        }

    }

    @Override
    @Transactional
    public void deleteSubCategory(DeleteItemRequestDTO deleteItemRequestDTO) {

        Item item = itemRepository.findById(deleteItemRequestDTO.getItemCode()).orElseThrow(
            () -> new EmptyDataException("삭제하려는 상품의 코드를 잘못 입력하였습니다")
        );

        item.setActive(false);

        itemRepository.save(item);

    }
}
