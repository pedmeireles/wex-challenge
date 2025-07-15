package com.example.wex_challenge.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id){
        super("Transaction with id "+id+" not found.");
    }
    
}
