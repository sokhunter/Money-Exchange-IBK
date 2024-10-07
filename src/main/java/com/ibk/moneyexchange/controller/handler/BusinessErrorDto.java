package com.ibk.moneyexchange.controller.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ibk.moneyexchange.controller.handler.exceptions.ClientException;
import com.ibk.moneyexchange.controller.handler.exceptions.DatabaseException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@Data
public class BusinessErrorDto {
    private HttpStatus status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public BusinessErrorDto(InvalidFormatException ex) {
        this.status = HttpStatus.BAD_REQUEST;
        this.message = "Datos ingresados inválidos";
        this.details = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public BusinessErrorDto(MethodArgumentNotValidException ex) {
        this.status = HttpStatus.BAD_REQUEST;
        this.message = "Datos ingresados inválidos";
        this.details = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public BusinessErrorDto(ClientException ex) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Error por el lado del servicio market-exchange";
        this.details = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public BusinessErrorDto(DatabaseException ex) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Error al momento de almacenar el registro en la base de datos";
        this.details = ex.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
