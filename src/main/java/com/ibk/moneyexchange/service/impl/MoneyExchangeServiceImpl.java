package com.ibk.moneyexchange.service.impl;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.controller.handler.exceptions.ClientException;
import com.ibk.moneyexchange.controller.handler.exceptions.DatabaseReadException;
import com.ibk.moneyexchange.controller.handler.exceptions.DatabaseWriteException;
import com.ibk.moneyexchange.repository.client.ExchangeMarketClient;
import com.ibk.moneyexchange.repository.client.response.ExchangeMarketResponse;
import com.ibk.moneyexchange.repository.database.MoneyExchangeRepository;
import com.ibk.moneyexchange.repository.database.entity.MoneyExchange;
import com.ibk.moneyexchange.service.MoneyExchangeService;
import com.ibk.moneyexchange.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MoneyExchangeServiceImpl implements MoneyExchangeService {
    private final MoneyExchangeRepository moneyExchangeRepository;
    private final ExchangeMarketClient exchangeMarketClient;

    @Override
    public MoneyExchangeDto generateExchange(MoneyExchangeDto moneyExchangeDto) {
        MoneyExchange moneyExchange;
        try {
            log.info("Getting exchange market");
            ExchangeMarketResponse exchangeMarket = this.exchangeMarketClient.getExchange(moneyExchangeDto.getOriginCurrency());
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
            throw new DatabaseWriteException(ex);
        }
        return MapperUtils.toMoneyExchangeDto(moneyExchange);
    }

    @Override
    public List<MoneyExchangeDto> getExchanges() {
        List<MoneyExchange> moneyExchanges;
        try {
            log.info("Getting exchanges");
            moneyExchanges = moneyExchangeRepository.findAll();

        } catch (Exception ex) {
            log.error("Error while getting money exchange list", ex);
            throw new DatabaseReadException(ex);
        }
        return MapperUtils.toMoneyExchangeDtoList(moneyExchanges);
    }
}
