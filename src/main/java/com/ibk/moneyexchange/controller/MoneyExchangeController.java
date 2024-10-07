package com.ibk.moneyexchange.controller;

import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.service.MoneyExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ibk")
@RestController
public class MoneyExchangeController {
    private final MoneyExchangeService moneyExchangeService;

    @PostMapping("/exchange")
    public MoneyExchangeDto exchange(@RequestBody MoneyExchangeDto exchangeDto) {
        log.info("Exchanging Money: from {} to {}, amount: {}",
                exchangeDto.getOriginCurrency(), exchangeDto.getTargetCurrency(), exchangeDto.getAmount());
        return moneyExchangeService.getMoneyExchange(exchangeDto);
    }
}