package com.example.wex_challenge.ratesExchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.wex_challenge.transaction.TransactionDto;

public final class EvaluateExchangeRate {
    

   public static BigDecimal evaluateExchangeRate(RatesDto RatesDto, TransactionDto transaction){

        BigDecimal exchangeRate = RatesDto.getExchangeRate();
        BigDecimal transactionAmount = transaction.getAmount();

        return exchangeRate.multiply(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);

    
   }
}
