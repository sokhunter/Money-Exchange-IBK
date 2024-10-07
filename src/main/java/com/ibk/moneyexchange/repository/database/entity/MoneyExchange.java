package com.ibk.moneyexchange.repository.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MONEY_EXCHANGE_IBK")
@Entity
public class MoneyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Double targetAmount;
    private Double exchangeRate;
    private String originCurrency;
    private String targetCurrency;
    private LocalDateTime createdAt;
}
