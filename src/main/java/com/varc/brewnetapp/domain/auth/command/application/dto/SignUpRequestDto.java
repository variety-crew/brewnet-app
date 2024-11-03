package com.varc.brewnetapp.domain.auth.command.application.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String id;
    private String password;
    private String name;
    private String nickname;
}
