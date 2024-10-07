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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MoneyExchangeServiceImplTest {
    private MoneyExchangeService moneyExchangeService;
    @Mock
    private MoneyExchangeRepository moneyExchangeRepository;
    @Mock
    private ExchangeMarketClient exchangeMarketClient;

    @BeforeEach
    void setUp() {
        moneyExchangeService = new MoneyExchangeServiceImpl(moneyExchangeRepository, exchangeMarketClient);
    }

    @Test
    void createShouldBeOkWhenDataIsCorrect() {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .build();

        Mockito.when(exchangeMarketClient.getExchange("USD"))
                .thenReturn(getExchangeMarketResponse());
        Mockito.when(moneyExchangeRepository.save(Mockito.any())).thenReturn(null);

        MoneyExchangeDto response = moneyExchangeService.generateExchange(request);
        Assertions.assertEquals(350.0, response.getTargetAmount());
    }

    @Test
    void shouldBeFailWhenDataIsIncorrect() {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("ABC")
                .targetCurrency("PEN")
                .build();

        Mockito.when(exchangeMarketClient.getExchange("ABC"))
                .thenThrow(new IllegalArgumentException());

        ClientException response = Assertions.assertThrows(
                ClientException.class, () -> moneyExchangeService.generateExchange(request));
        Assertions.assertEquals("Error por el lado del servicio market-exchange", response.getMessage());
    }

    @Test
    void shouldBeFailWhenWriteIntoDatabaseFails() {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .build();

        Mockito.when(exchangeMarketClient.getExchange("USD"))
                .thenReturn(getExchangeMarketResponse());
        Mockito.when(moneyExchangeRepository.save(Mockito.any()))
                .thenThrow(new RuntimeException());

        DatabaseWriteException response = Assertions.assertThrows(
                DatabaseWriteException.class, () -> moneyExchangeService.generateExchange(request));
        Assertions.assertEquals("Error al momento de almacenar el registro en la base de datos", response.getMessage());
    }

    private ExchangeMarketResponse getExchangeMarketResponse() {
        ExchangeMarketResponse exchangeMarketResponse = new ExchangeMarketResponse();
        exchangeMarketResponse.setRates(Map.of("PEN", 3.5));
        return exchangeMarketResponse;
    }

    @Test
    void readShouldBeOkWhenAllIsUp() {
        MoneyExchange moneyExchange = MoneyExchange.builder()
                .amount(100.0)
                .targetAmount(372.6674)
                .exchangeRate(3.726674)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .createdAt(LocalDateTime.now())
                .build();

        List<MoneyExchange> databaseResponse = List.of(moneyExchange, moneyExchange);
        Mockito.when(moneyExchangeRepository.findAll()).thenReturn(databaseResponse);

        List<MoneyExchangeDto> response = moneyExchangeService.getExchanges();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void readShouldBeFailWhenDatabaseIsDown() {
        Mockito.when(moneyExchangeRepository.findAll()).thenThrow(new RuntimeException());

        DatabaseReadException response = Assertions.assertThrows(
                DatabaseReadException.class, () -> moneyExchangeService.getExchanges());
        Assertions.assertEquals("Error al momento de leer los registros de la base de datos", response.getMessage());
    }
}