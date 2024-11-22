package com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_franchise_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseMember {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franchise_member_code", nullable = false)
    private Integer franchiseMemberCode;

    @Column(name = "franchise_code", nullable = false)
    private Integer franchiseCode;

    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

}