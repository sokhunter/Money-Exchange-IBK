package com.ibk.moneyexchange.controller.handler.exceptions;

import com.ibk.moneyexchange.utils.MessageUtils;
import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends BusinessException {
    public UserAuthenticationException(Exception ex) {
        super(HttpStatus.UNAUTHORIZED, MessageUtils.getMessage("authentication.error"), ex);
    }
}
