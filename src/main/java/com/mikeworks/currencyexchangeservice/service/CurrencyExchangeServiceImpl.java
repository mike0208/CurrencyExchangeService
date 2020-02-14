package com.mikeworks.currencyexchangeservice.service;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mikeworks.currencyexchangeservice.model.ExchangeValue;
import com.mikeworks.currencyexchangeservice.model.ExchangeValueDto;
import com.mikeworks.currencyexchangeservice.repository.ExchangeValueRepository;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {


	private ExchangeValueRepository exchangeValueRepository;
	private Environment env;
	private static Long COUNT = 0L;

	@Autowired
	public CurrencyExchangeServiceImpl(ExchangeValueRepository exchangeValueRepository, Environment env) {
		this.exchangeValueRepository = exchangeValueRepository;
		this.env = env;
	}

	@Override
	public ExchangeValueDto getCurrency(String from, String to) {
		ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ExchangeValueDto returnExchangeValue = modelMapper.map(exchangeValue, ExchangeValueDto.class);
		returnExchangeValue.setPort(Integer.valueOf(env.getProperty("server.port")));
		return returnExchangeValue;
	}

	@Override
	public ExchangeValueDto createCurrency(String from, String to, int conversionmultiple) {
		BigDecimal conversionMultiple = BigDecimal.valueOf(conversionmultiple);
		//Long id = COUNT + 1;
		ExchangeValue exchangeValueEntity = new ExchangeValue(from, to, conversionMultiple);
		
		exchangeValueRepository.save(exchangeValueEntity);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ExchangeValueDto returnExchangeValue = modelMapper.map(exchangeValueEntity, ExchangeValueDto.class);
		returnExchangeValue.setPort(Integer.valueOf(env.getProperty("server.port")));
		return returnExchangeValue;
	}

}
