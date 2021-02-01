package com.microservices.currencyconversionservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CurrencyService {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@HystrixCommand(fallbackMethod="defaultMethod")
	public CurrencyConversion retrieve(String from, String to) {
		return proxy.retrieve(from, to);
	}
	
	public CurrencyConversion defaultMethod(String from, String to) {
		CurrencyConversion def = new CurrencyConversion(0L,"0","0",BigDecimal.valueOf(0),
				BigDecimal.valueOf(0),BigDecimal.valueOf(0),0);
		return def;
		
	}

}
