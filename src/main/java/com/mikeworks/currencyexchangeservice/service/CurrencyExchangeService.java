package com.mikeworks.currencyexchangeservice.service;


import com.mikeworks.currencyexchangeservice.model.ExchangeValueDto;

public interface CurrencyExchangeService {

	public ExchangeValueDto getCurrency(String from, String to);

	public ExchangeValueDto createCurrency(String from, String to, int conversionmultiple);
}
