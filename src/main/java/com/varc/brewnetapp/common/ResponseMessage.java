package com.varc.brewnetapp.common;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {
    private int status;
    private String message;
    private T result;
}
