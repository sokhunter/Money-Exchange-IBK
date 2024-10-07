package com.ibk.moneyexchange.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MoneyExchangeDto {
    private Double amount;
    private Double targetAmount;
    private Double exchangeRate;
    private String originCurrency;
    private String targetCurrency;
}
