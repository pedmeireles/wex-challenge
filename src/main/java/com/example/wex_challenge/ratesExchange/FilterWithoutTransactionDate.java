package com.example.wex_challenge.ratesExchange;
 
public class FilterWithoutTransactionDate extends RuntimeException {
    public FilterWithoutTransactionDate(){
        super("Filter does not have a transaction date setup");
    }
    
}
