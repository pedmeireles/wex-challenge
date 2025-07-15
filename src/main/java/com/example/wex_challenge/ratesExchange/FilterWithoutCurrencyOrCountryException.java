package com.example.wex_challenge.ratesExchange;
 
public class FilterWithoutCurrencyOrCountryException extends RuntimeException {
    public FilterWithoutCurrencyOrCountryException(){
        super("Filter does not have neither a country or a currency setup");
    }
    
}
