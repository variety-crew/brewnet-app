package com.varc.brewnetapp.security.vo;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestVO {
    private String loginId;
    private String password;
}
