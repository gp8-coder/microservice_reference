package com.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment env;
	@Autowired
	private ExchangeRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieve(@PathVariable String from,@PathVariable String to) {
		
	//	ExchangeValue e= new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
		
		ExchangeValue e = repository.findByFromAndTo(from, to);
	
		e.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return e;
	}
}
