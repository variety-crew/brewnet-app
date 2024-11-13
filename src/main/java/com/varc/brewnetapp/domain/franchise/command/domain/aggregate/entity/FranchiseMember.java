package com.varc.brewnetapp.domain.franchise.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.franchise.command.domain.aggregate.FranchiseMemberId;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_franchise_member")
@IdClass(FranchiseMemberId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseMember {

    @Id
    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Id
    @Column(name = "franchise_code", nullable = false)
    private Integer franchiseCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    // Foreign key 관계 설정
    @ManyToOne
    @JoinColumn(name = "member_code")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "franchise_code")
    private Franchise franchise;

}