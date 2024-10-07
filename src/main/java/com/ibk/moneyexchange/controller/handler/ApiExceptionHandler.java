package com.ibk.moneyexchange.controller.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ibk.moneyexchange.controller.handler.exceptions.ClientException;
import com.ibk.moneyexchange.controller.handler.exceptions.DatabaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<BusinessErrorDto> handleIllegalArgumentException(InvalidFormatException ex) {
        return ResponseEntity.badRequest().body(new BusinessErrorDto(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BusinessErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(new BusinessErrorDto(ex));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<BusinessErrorDto> handleClientException(ClientException ex) {
        return ResponseEntity.internalServerError().body(new BusinessErrorDto(ex));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<BusinessErrorDto> handleDatabaseException(DatabaseException ex) {
        return ResponseEntity.internalServerError().body(new BusinessErrorDto(ex));
    }
}
