package com.varc.brewnetapp.domain.auth.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_member_role")
@IdClass(MemberRolePK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {
    @Id
    @Column(name = "member_code")
    private int memberCode;

    @Id
    @Column(name = "role_code")
    private int roleCode;
}
