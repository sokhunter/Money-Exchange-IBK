package com.ibk.moneyexchange.controller.handler;

import com.ibk.moneyexchange.controller.handler.exceptions.BusinessException;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class BusinessErrorDto {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    public BusinessErrorDto() {
        this.status = HttpStatus.BAD_REQUEST;
        this.message = "Datos ingresados inválidos";
        this.timestamp = LocalDateTime.now();
    }

    public BusinessErrorDto(BusinessException ex) {
        this.status = ex.getStatus();
        this.message = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
