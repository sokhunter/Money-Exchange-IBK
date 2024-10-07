package com.ibk.moneyexchange.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MoneyExchangeDto {
    @NotNull
    @Positive
    private Double amount;
    private Double targetAmount;
    private Double exchangeRate;
    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private String originCurrency;
    @NotNull
    @Pattern(regexp = "[A-Z]{3}")
    private String targetCurrency;
}
