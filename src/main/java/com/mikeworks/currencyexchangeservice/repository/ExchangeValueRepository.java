package com.mikeworks.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikeworks.currencyexchangeservice.model.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

	ExchangeValue findByFromAndTo(String from,String to);
	
	
}
