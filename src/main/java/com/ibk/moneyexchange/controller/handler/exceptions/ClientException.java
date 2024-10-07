package com.ibk.moneyexchange.controller.handler.exceptions;

import com.ibk.moneyexchange.utils.MessageUtils;
import org.springframework.http.HttpStatus;

public class ClientException extends BusinessException {
    public ClientException(Exception ex) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, MessageUtils.getMessage("client.market-exchange.error"), ex);
    }
}
