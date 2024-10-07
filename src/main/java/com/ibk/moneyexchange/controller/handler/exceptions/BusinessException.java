package com.ibk.moneyexchange.controller.handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public BusinessException(HttpStatus status, String message, Exception ex) {
        super(ex);
        this.status = status;
        this.message = message;
    }
}
