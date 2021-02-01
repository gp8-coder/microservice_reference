package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConvertionController {

	@Autowired
	private CurrencyService proxy;
	
	@GetMapping("/")
	public String test() {
		return "success";
	}
	
	@GetMapping("/currency-convertor/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		Map<String,String> urivariables = new HashMap<>();
		urivariables.put("from", from);
		urivariables.put("to",to);
		ResponseEntity<CurrencyConversion> response = 
				new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,urivariables);
		
		CurrencyConversion r = response.getBody();
		return new CurrencyConversion(r.getId(),from,to,r.getConversionMultiple(),quantity,
				quantity.multiply(r.getConversionMultiple()),r.getPort());
	}
	
	@GetMapping("/currency-convertor-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
	
		CurrencyConversion r = proxy.retrieve(from, to);
		return new CurrencyConversion(r.getId(),from,to,r.getConversionMultiple(),quantity,
				quantity.multiply(r.getConversionMultiple()),r.getPort());
	
	}
}
