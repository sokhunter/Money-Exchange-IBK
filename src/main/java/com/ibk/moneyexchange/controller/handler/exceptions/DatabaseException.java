package com.ibk.moneyexchange.controller.handler.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(Exception ex) {
        super(ex);
    }
}
