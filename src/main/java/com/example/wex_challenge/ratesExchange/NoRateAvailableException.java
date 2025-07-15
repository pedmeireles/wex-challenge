package com.example.wex_challenge.ratesExchange;

public class NoRateAvailableException extends RuntimeException {
    public NoRateAvailableException(){
        super("No rate available for this conditions");
    }
    
}
