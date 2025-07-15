package com.example.wex_challenge.rates_exchange;

public class NoRateAvailableException extends RuntimeException {
    public NoRateAvailableException(){
        super("No rate available for this conditions");
    }
    
}
