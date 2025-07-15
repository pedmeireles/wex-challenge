package com.example.wex_challenge.rates_exchange;

import java.time.LocalDate;

import com.example.wex_challenge.transaction.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {

    private String country;
    private String currency;

    private String countryCurrencyDesc;

    
    private LocalDate transactionDate;


    public void setTransactionDateFromTransaction(Transaction transaction){
        this.transactionDate = transaction.getCreatedDate().minusMonths(6);
    }
    
}
