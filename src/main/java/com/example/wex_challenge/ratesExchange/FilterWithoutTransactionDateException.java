package com.example.wex_challenge.ratesExchange;
 
public class FilterWithoutTransactionDateException extends RuntimeException {
    public FilterWithoutTransactionDateException(){
        super("Filter does not have a transaction date setup");
    }
    
}
