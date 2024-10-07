package com.ibk.moneyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibk.moneyexchange.config.TestConfig;
import com.ibk.moneyexchange.controller.dto.MoneyExchangeDto;
import com.ibk.moneyexchange.controller.handler.exceptions.ClientException;
import com.ibk.moneyexchange.service.MoneyExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
class MoneyExchangeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MoneyExchangeService moneyExchangeService;

    @Test
    void createShouldBeOkWhenDataIsCorrect() throws Exception {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .build();

        MoneyExchangeDto response = MoneyExchangeDto.builder()
                .amount(100.0)
                .targetAmount(372.6674)
                .exchangeRate(3.726674)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .build();

        Mockito.when(moneyExchangeService.generateExchange(request))
                .thenReturn(response);

        MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders
                .post(URI_MONEY_EXCHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        mockMvc.perform(mockHttp)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(response)));
    }

    private final String URI_MONEY_EXCHANGE = "/api/ibk/exchange";

    @Test
    void shouldBeFailWhenCurrencyIsIncorrect() throws Exception {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("USD")
                .targetCurrency("ABC")
                .build();

        Mockito.when(moneyExchangeService.generateExchange(request))
                .thenThrow(new ClientException(new RuntimeException("Currency ABC is invalid")));

        MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders
                .post(URI_MONEY_EXCHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        mockMvc.perform(mockHttp)
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error por el lado del servicio market-exchange"));
    }

    @Test
    void shouldBeFailWhenAmountIsZero() throws Exception {
        MoneyExchangeDto request = MoneyExchangeDto.builder()
                .amount(0.0)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .build();

        MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders
                .post(URI_MONEY_EXCHANGE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        mockMvc.perform(mockHttp)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Datos ingresados inválidos"));
    }

    @Test
    void listShouldBeOkWhenAllIsUp() throws Exception {
        MockHttpServletRequestBuilder mockHttp = MockMvcRequestBuilders
                .get(URI_MONEY_EXCHANGE);

        MoneyExchangeDto moneyExchangeDto = MoneyExchangeDto.builder()
                .amount(100.0)
                .originCurrency("USD")
                .targetCurrency("PEN")
                .exchangeRate(3.726674)
                .targetAmount(372.6674)
                .build();

        List<MoneyExchangeDto> response = List.of(moneyExchangeDto, moneyExchangeDto);
        Mockito.when(moneyExchangeService.getExchanges())
                .thenReturn(response);

        mockMvc.perform(mockHttp)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100.0));
    }
}