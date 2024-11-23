package com.varc.brewnetapp.domain.storage.command.application.service;

import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseItem;
import com.varc.brewnetapp.domain.purchase.command.domain.repository.PurchaseItemRepository;
import com.varc.brewnetapp.domain.storage.command.application.dto.ChangeStockRequestDTO;
import com.varc.brewnetapp.domain.storage.command.application.dto.StorageRequestDTO;
import com.varc.brewnetapp.domain.storage.command.domain.aggregate.Stock;
import com.varc.brewnetapp.domain.storage.command.domain.aggregate.Storage;
import com.varc.brewnetapp.domain.storage.command.domain.repository.StockRepository;
import com.varc.brewnetapp.domain.storage.command.domain.repository.StorageRepository;
import com.varc.brewnetapp.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("StorageServiceCommand")
public class StorageServiceImpl implements StorageService{

    private final ModelMapper modelMapper;
    private final StorageRepository storageRepository;
    private final StockRepository stockRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public StorageServiceImpl(ModelMapper modelMapper,
                              StorageRepository storageRepository,
                              StockRepository stockRepository,
                              PurchaseItemRepository purchaseItemRepository,
                              MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.storageRepository = storageRepository;
        this.stockRepository = stockRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public void createStorage(String loginId, StorageRequestDTO newStorage) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        // 창고 먼저 저장
        Storage storage = modelMapper.map(newStorage, Storage.class);
        storage.setActive(true);
        storage.setCreatedAt(LocalDateTime.now());
        storageRepository.save(storage);

        // 창고별 상품 재고 모두 0으로 등록
        List<PurchaseItem> items = purchaseItemRepository.findByActiveTrue();
        for (PurchaseItem item : items) {
            Stock stock = new Stock();
            stock.setStorageCode(storage.getStorageCode());
            stock.setItemCode(item.getItemCode());
            stock.setAvailableStock(0);
            stock.setOutStock(0);
            stock.setInStock(0);
            stock.setCreatedAt(LocalDateTime.now());
            stock.setActive(true);
            stockRepository.save(stock);
        }
    }

    @Transactional
    @Override
    public void editStorage(String loginId, int storageCode, StorageRequestDTO editedStorage) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Storage storage = storageRepository.findByStorageCodeAndActiveTrue(storageCode);
        if (storage == null) throw new StorageNotFoundException("삭제되었거나 존재하지 않는 창고입니다.");

        // 수정한 정보 저장
        if (editedStorage.getName() != null) storage.setName(editedStorage.getName());
        if (editedStorage.getAddress() != null) storage.setAddress(editedStorage.getAddress());
        if (editedStorage.getContact() != null) storage.setContact(editedStorage.getContact());
    }

    @Transactional
    @Override
    public void deleteStorage(String loginId, int storageCode) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Storage storage = storageRepository.findByStorageCodeAndActiveTrue(storageCode);
        if (storage == null) throw new StorageNotFoundException("삭제되었거나 존재하지 않는 창고입니다.");

        List<Stock> stockList = stockRepository.findByStorageCodeAndActiveTrue(storageCode);

        // 창고별 상품 재고가 모두 0인지 체크하여 0이면 창고의 해당 상품 삭제 처리
        for (Stock stock : stockList) {
            if (!(stock.getAvailableStock()).equals(0) ||
                    !(stock.getOutStock()).equals(0) ||
                    !(stock.getInStock()).equals(0)) {
                throw new InvalidDataException("해당 창고에 상품의 재고가 남아 있습니다.");
            }
            else if (stock.getAvailableStock().equals(0) &&
                    stock.getOutStock().equals(0) &&
                    stock.getInStock().equals(0)) {
                stock.setActive(false);
            }
        }

        // 창고별 상품 재고가 모두 삭제 처리된 것을 확인하면 창고 삭제 처리
        List<Stock> stockCheckList = stockRepository.findByStorageCodeAndActiveTrue(storageCode);
        if (stockCheckList == null) storage.setActive(false);
    }

    @Transactional
    @Override
    public void changeStock(String loginId, int storageCode, List<ChangeStockRequestDTO> changes) {

        // 로그인한 사용자 체크
        memberRepository.findById(loginId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        // 창고 선택
        Storage storage = storageRepository.findByStorageCodeAndActiveTrue(storageCode);
        if (storage == null) throw new StorageNotFoundException("삭제되었거나 존재하지 않는 창고입니다.");

        for (ChangeStockRequestDTO change : changes) {
            Stock itemStock = stockRepository
                                .findByStorageCodeAndItemCode(storage.getStorageCode(), change.getItemCode());

            if (itemStock == null) throw new ItemNotFoundException("창고에 존재하지 않는 상품입니다.");
            if (!(itemStock.getActive()).equals(true)) throw new ItemNotFoundException("창고에서 삭제된 상품입니다.");
            if (change.getQuantity() == 0) throw new InvalidDataException("재고에 합산할 수량을 입력해 주세요.");

            // 가용재고에 재입고된 수량 합산
            int changedStock = itemStock.getAvailableStock() + change.getQuantity();
            itemStock.setAvailableStock(changedStock);
        }
    }
}
