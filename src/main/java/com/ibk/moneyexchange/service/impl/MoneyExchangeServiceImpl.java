package com.ibk.moneyexchange.service.impl;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.controller.handler.exceptions.ClientException;
import com.ibk.moneyexchange.controller.handler.exceptions.DatabaseException;
import com.ibk.moneyexchange.proxy.ExchangeMarket;
import com.ibk.moneyexchange.proxy.response.ExchangeMarketResponse;
import com.ibk.moneyexchange.repository.database.MoneyExchangeRepository;
import com.ibk.moneyexchange.repository.database.entity.MoneyExchange;
import com.ibk.moneyexchange.service.MoneyExchangeService;
import com.ibk.moneyexchange.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MoneyExchangeServiceImpl implements MoneyExchangeService {
    private final MoneyExchangeRepository moneyExchangeRepository;
    private final ExchangeMarket exchangeMarket;

    @Override
    public MoneyExchangeDto getMoneyExchange(MoneyExchangeDto moneyExchangeDto) {
        MoneyExchange moneyExchange;
        try {
            log.info("Getting exchange market");
            ExchangeMarketResponse exchangeMarket = this.exchangeMarket.getExchange(moneyExchangeDto.getOriginCurrency());
            moneyExchange = MapperUtils.toMoneyExchange(exchangeMarket, moneyExchangeDto);
        } catch (Exception ex) {
            log.error("Error while getting money exchange", ex);
            throw new ClientException(ex);
        }
        try {
            log.info("Saving new currency exchange record");
            moneyExchangeRepository.save(moneyExchange);
        } catch (Exception ex) {
            log.error("Error while saving money exchange", ex);
            throw new DatabaseException(ex);
        }
        return MapperUtils.toMoneyExchangeDto(moneyExchange);
    }
}
