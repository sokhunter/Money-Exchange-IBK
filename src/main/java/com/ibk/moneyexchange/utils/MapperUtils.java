package com.ibk.moneyexchange.utils;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.proxy.response.ExchangeMarketResponse;
import com.ibk.moneyexchange.repository.database.entity.MoneyExchange;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class MapperUtils {
    public static MoneyExchange toMoneyExchange(ExchangeMarketResponse exchangeMarket, MoneyExchangeDto moneyExchangeDto) {
        return MoneyExchange.builder()
                .amount(moneyExchangeDto.getAmount())
                .targetAmount(getTargetAmount(
                    moneyExchangeDto.getAmount(),
                    exchangeMarket.getRates().get(moneyExchangeDto.getTargetCurrency())))
                .exchangeRate(exchangeMarket.getRates().get(moneyExchangeDto.getTargetCurrency()))
                .originCurrency(moneyExchangeDto.getOriginCurrency())
                .targetCurrency(moneyExchangeDto.getTargetCurrency())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MoneyExchangeDto toMoneyExchangeDto(MoneyExchange moneyExchange) {
        return MoneyExchangeDto.builder()
                .amount(moneyExchange.getAmount())
                .targetCurrency(moneyExchange.getTargetCurrency())
                .exchangeRate(moneyExchange.getExchangeRate())
                .originCurrency(moneyExchange.getOriginCurrency())
                .targetAmount(moneyExchange.getTargetAmount())
                .build();
    }

    private Double getTargetAmount(Double amount, Double rate) {
        return amount * rate;
    }
}
