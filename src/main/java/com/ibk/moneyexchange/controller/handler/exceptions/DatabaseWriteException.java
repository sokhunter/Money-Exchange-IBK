package com.ibk.moneyexchange.controller.handler.exceptions;

import com.ibk.moneyexchange.utils.MessageUtils;
import org.springframework.http.HttpStatus;

public class DatabaseWriteException extends BusinessException {
    public DatabaseWriteException(Exception ex) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("database.write.error"), ex);
    }
}
