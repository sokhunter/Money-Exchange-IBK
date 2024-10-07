package com.ibk.moneyexchange.utils;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.controller.dto.UserDto;
import com.ibk.moneyexchange.repository.client.response.ExchangeMarketResponse;
import com.ibk.moneyexchange.repository.database.entity.MoneyExchange;
import com.ibk.moneyexchange.repository.database.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

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

    public static List<MoneyExchangeDto> toMoneyExchangeDtoList(List<MoneyExchange> moneyExchanges) {
        return moneyExchanges.stream().map(MapperUtils::toMoneyExchangeDto).toList();
    }

    public static UserEntity toUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    private Double getTargetAmount(Double amount, Double rate) {
        return amount * rate;
    }
}
