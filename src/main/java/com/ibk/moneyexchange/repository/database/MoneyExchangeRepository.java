package com.ibk.moneyexchange.repository.database;

import com.ibk.moneyexchange.repository.database.entity.MoneyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyExchangeRepository extends JpaRepository<MoneyExchange, Long> {

}
