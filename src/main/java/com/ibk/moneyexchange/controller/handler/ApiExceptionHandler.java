package com.ibk.moneyexchange.controller.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ibk.moneyexchange.controller.handler.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({InvalidFormatException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<BusinessErrorDto> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().body(new BusinessErrorDto());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorDto> handleClientException(BusinessException ex) {
        return ResponseEntity.internalServerError().body(new BusinessErrorDto(ex));
    }
}
