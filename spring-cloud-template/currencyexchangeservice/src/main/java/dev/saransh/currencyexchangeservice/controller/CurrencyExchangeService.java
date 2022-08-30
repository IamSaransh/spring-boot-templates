package dev.saransh.currencyexchangeservice.controller;

import dev.saransh.currencyexchangeservice.model.CurrencyExchange;
import dev.saransh.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class CurrencyExchangeService {
    @Autowired
    private CurrencyExchangeRepository repository;
    @Autowired
    private Environment environment;

    //http://localhost:8000/currency-exchange/from/USD/to/INR
    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange==null){
            throw new RuntimeException("not able to find your data in db for " +  from + "to " +  to);
        }
        String currPort = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(currPort);
        return currencyExchange;
    }
}
