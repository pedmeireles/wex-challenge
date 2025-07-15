package com.example.wex_challenge.ratesExchange;
 
public class FilterWithoutCurrencyOrCountry extends RuntimeException {
    public FilterWithoutCurrencyOrCountry(){
        super("Filter does not have neither a country or a currency setup");
    }
    
}
