package com.mikeworks.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikeworks.currencyexchangeservice.model.ExchangeValueDto;
import com.mikeworks.currencyexchangeservice.service.CurrencyExchangeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//@RequestMapping("/api/v1")
public class CurrencyExchangeServiceController {

	@Autowired
	private Environment environment;
	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	/*
	 * @GetMapping("/demo") public String demo() { return "working"; }
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValueDto retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		/*
		 * BigDecimal conversionMultiple=BigDecimal.valueOf(0); switch (from) { case
		 * "USD": conversionMultiple=BigDecimal.valueOf(65); break; case "EURO":
		 * conversionMultiple=BigDecimal.valueOf(75); break; case "POUND":
		 * conversionMultiple=BigDecimal.valueOf(85); break; default: break; }
		 */

		ExchangeValueDto exchangeValueDto = currencyExchangeService.getCurrency(from, to);

		// exchangeValue.setPort(8000);
		// exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

	@PostMapping("/currency-exchange/from/{from}/to/{to}/conversionmultiple/{conversionmultiple}")
	public ExchangeValueDto createExchangeValue(@PathVariable String from, @PathVariable String to,
			@PathVariable String conversionmultiple) {

		int conversionMultiple = Integer.valueOf(conversionmultiple);
		ExchangeValueDto exchangeValueDto = currencyExchangeService.createCurrency(from, to, conversionMultiple);

		// exchangeValue.setPort(8000);
		// exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

	@GetMapping("/currency-exchange-using-hystrix/from/{from}/to/{to}")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveExchangeValue")
	public ExchangeValueDto retrieveExchangeValueWithHystrix(@PathVariable String from, @PathVariable String to) {

		/*
		 * BigDecimal conversionMultiple=BigDecimal.valueOf(0); switch (from) { case
		 * "USD": conversionMultiple=BigDecimal.valueOf(65); break; case "EURO":
		 * conversionMultiple=BigDecimal.valueOf(75); break; case "POUND":
		 * conversionMultiple=BigDecimal.valueOf(85); break; default: break; }
		 */

		ExchangeValueDto exchangeValueDto = currencyExchangeService.getCurrency(from, to);

		// exchangeValue.setPort(8000);
		// exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

	public ExchangeValueDto fallbackRetrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		/*
		 * BigDecimal conversionMultiple=BigDecimal.valueOf(0); switch (from) { case
		 * "USD": conversionMultiple=BigDecimal.valueOf(65); break; case "EURO":
		 * conversionMultiple=BigDecimal.valueOf(75); break; case "POUND":
		 * conversionMultiple=BigDecimal.valueOf(85); break; default: break; }
		 */

		ExchangeValueDto exchangeValueDto = new ExchangeValueDto();
		exchangeValueDto.setFrom("default");
		exchangeValueDto.setTo("INR");

		// exchangeValue.setPort(8000);
		exchangeValueDto.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

	@PostMapping("/currency-exchange-using-hystrix/from/{from}/to/{to}/conversionmultiple/{conversionmultiple}")
	@HystrixCommand(fallbackMethod = "fallbackCreateExchangeValue")
	public ExchangeValueDto createExchangeValueWithHystrix(@PathVariable String from, @PathVariable String to,
			@PathVariable String conversionmultiple) {

		int conversionMultiple = Integer.valueOf(conversionmultiple);
		ExchangeValueDto exchangeValueDto = currencyExchangeService.createCurrency(from, to, conversionMultiple);

		// exchangeValue.setPort(8000);
		// exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

	public ExchangeValueDto fallbackCreateExchangeValue(@PathVariable String from, @PathVariable String to,
			@PathVariable String conversionmultiple) {

		int conversionMultiple = Integer.valueOf(conversionmultiple);
		ExchangeValueDto exchangeValueDto = currencyExchangeService.createCurrency(from, to, conversionMultiple);
		exchangeValueDto.setFrom("default");
		exchangeValueDto.setTo("INR");
		// exchangeValue.setPort(8000);
		// exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return exchangeValueDto;
	}

}
