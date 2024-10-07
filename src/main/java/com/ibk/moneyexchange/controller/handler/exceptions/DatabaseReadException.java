package com.ibk.moneyexchange.controller.handler.exceptions;

import com.ibk.moneyexchange.utils.MessageUtils;
import org.springframework.http.HttpStatus;

public class DatabaseReadException extends BusinessException {
    public DatabaseReadException(Exception ex) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("database.read.error"), ex);
    }
}
