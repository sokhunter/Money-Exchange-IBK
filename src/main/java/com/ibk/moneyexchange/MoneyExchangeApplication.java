package com.ibk.moneyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoneyExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyExchangeApplication.class, args);
	}

}
