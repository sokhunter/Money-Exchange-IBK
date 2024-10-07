package com.ibk.moneyexchange.controller.handler.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(Exception ex) {
        super(ex);
    }
}
