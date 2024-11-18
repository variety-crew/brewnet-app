package com.varc.brewnetapp.domain.auth.command.domain.aggregate.entity;

import com.varc.brewnetapp.domain.auth.command.domain.aggregate.MemberRolePK;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_member_role")
@IdClass(MemberRolePK.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {
    @Id
    @Column(name = "member_code")
    private int memberCode;

    @Id
    @Column(name = "role_code")
    private int roleCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active;

}
