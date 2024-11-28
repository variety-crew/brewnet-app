package com.varc.brewnetapp.domain.returning.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Data
@Getter
@Entity
@Table(name = "tbl_return_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ReturningImg {
    @Id
    @Column(name = "return_img_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int returningImgCode;   // 반품 품목 사진 코드

    @Column(name = "imag_url", nullable = false)
    private String imageUrl;        // 반품 이미지 url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_code", nullable = false)
    private Returning returning;    // 반품 코드
}