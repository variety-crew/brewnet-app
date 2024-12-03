//package com.varc.brewnetapp.domain.delivery.command.domain.aggregate.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "tbl_exchange_item_status")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class DelExStockItem {
//
//    @ManyToOne
//    @JoinColumn(name = "exchange_stock_history_code", referencedColumnName = "exchange_stock_history_code", nullable = false)
//    private TblExchangeStockHistory tblExchangeStockHistory;
//
//    @ManyToOne
//    @JoinColumn(name = "item_code", referencedColumnName = "item_code", nullable = false)
//    private TblItem tblItem;
//
//    @Column(name = "quantity", nullable = false)
//    private int quantity;
//
//    @Column(name = "restock_quantity", nullable = false)
//    private int restockQuantity;
//
//    // Getters and Setters
//}