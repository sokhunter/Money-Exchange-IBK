package com.ibk.moneyexchange.service;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;

import java.util.List;

public interface MoneyExchangeService {
    MoneyExchangeDto generateExchange(MoneyExchangeDto moneyExchangeDto);
}
