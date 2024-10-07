package com.ibk.moneyexchange.proxy;

import com.ibk.moneyexchange.proxy.response.ExchangeMarketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "open-er-api", url="${proxy.exchange-market.url}")
public interface ExchangeMarket {
    @GetMapping("${proxy.exchange-market.path}")
    ExchangeMarketResponse getExchange(@PathVariable String currency);
}
