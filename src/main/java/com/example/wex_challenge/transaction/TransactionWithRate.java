package com.example.wex_challenge.transaction;

import java.math.BigDecimal;

import com.example.wex_challenge.ratesExchange.RatesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionWithRate extends TransactionDto {
    private RatesDto exchangeRateData;

    private BigDecimal convertedRate;

    public TransactionWithRate(TransactionDto transaction, RatesDto rate, BigDecimal converted){        
        super(transaction.getId(), transaction.getDescription(), transaction.getCreatedDate(), transaction.getAmount());
        this.convertedRate = converted;
        this.exchangeRateData=rate;
    }
}
