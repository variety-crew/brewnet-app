package com.varc.brewnetapp.domain.storage.command.application.service;

import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.purchase.command.domain.aggregate.PurchaseItem;
import com.varc.brewnetapp.domain.purchase.command.domain.repository.PurchaseItemRepository;
import com.varc.brewnetapp.domain.storage.command.application.dto.StorageRequestDTO;
import com.varc.brewnetapp.domain.storage.command.domain.aggregate.Stock;
import com.varc.brewnetapp.domain.storage.command.domain.aggregate.Storage;
import com.varc.brewnetapp.domain.storage.command.domain.repository.StockRepository;
import com.varc.brewnetapp.domain.storage.command.domain.repository.StorageRepository;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.exception.StorageNotFoundException;
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
}
