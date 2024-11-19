package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_exchange_img")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExchangeImg {
    @Id
    @Column(name = "exchange_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exchangeImgCode;    // 교환 품목 사진 코드

    @Column(name = "image_url", nullable = false)
    private String imageUrl;        // 교환 이미지 url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_code", nullable = false)
    private Exchange exchange;     // 교환 코드
}
