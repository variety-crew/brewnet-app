package com.varc.brewnetapp.domain.notice.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateNoticeRequestDTO {

    private int noticeCode;
    private String title;
    private String content;
}
