package com.varc.brewnetapp.domain.auth.query.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVO {
    private int memberCode;
    private String id;
    private String password;
    private String name;
    private String nickname;
    private List<RoleVO> roleVOList;
}
