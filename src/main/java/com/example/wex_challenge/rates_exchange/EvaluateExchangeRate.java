package com.example.wex_challenge.rates_exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.wex_challenge.transaction.TransactionDto;

public final class EvaluateExchangeRate {
    private EvaluateExchangeRate(){}
    

   public static BigDecimal evaluateExchangeRate(RatesDto ratesDto, TransactionDto transaction){

        BigDecimal exchangeRate = ratesDto.getExchangeRate();
        BigDecimal transactionAmount = transaction.getAmount();

        return exchangeRate.multiply(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);

    
   }
}
