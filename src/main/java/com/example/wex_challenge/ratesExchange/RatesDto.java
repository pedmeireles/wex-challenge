package com.example.wex_challenge.ratesExchange;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesDto {
    
    @JsonProperty("effective_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private  LocalDate effectiveDate;
    
    @JsonProperty("record_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private  LocalDate recordDate;
   
    private  String country;
   
    @JsonProperty("country_currency_desc")
    private  String countryCurrencyDesc;
   
    private  String currency;

   
    @JsonProperty("exchange_rate")
    private  BigDecimal exchangeRate;


}

