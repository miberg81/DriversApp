package com.michael.microservices.currencyexchangeservice.bean;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
	
	// will be implemented by Spring Data JPA (will convert this call into query)
	CurrencyExchange findByFromAndTo(String from, String to);

}
