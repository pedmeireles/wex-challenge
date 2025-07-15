package com.example.wex_challenge.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wex_challenge.ratesExchange.EvaluateExchangeRate;
import com.example.wex_challenge.ratesExchange.FilterDto;
import com.example.wex_challenge.ratesExchange.NoRateAvailableException;
import com.example.wex_challenge.ratesExchange.RatesDto;
import com.example.wex_challenge.ratesExchange.RatesExchangeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    public final TransactionService service;
    public final RatesExchangeService ratesExchangeService;

    public TransactionController(TransactionService service, RatesExchangeService ratesExchangeService) {
        this.service = service;
        this.ratesExchangeService = ratesExchangeService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody @Valid TransactionDto dto) {
        return ResponseEntity.ok().body(service.createTransaction(dto));

    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(service.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.getTransactionById(id));
    }

    @GetMapping("/{id}/exchange-rate")
    public ResponseEntity<TransactionWithRate> testFetchingFromFiscalData(
            @PathVariable("id") Long id,
            @RequestParam(required = false, defaultValue = "", name = "country") String country,
            @RequestParam(required = false, defaultValue = "", name = "currency") String currency,
            @RequestParam(required = false, defaultValue = "", name = "countryCurrency") String countryCurrency

    ) {

        TransactionDto transaction = service.getTransactionById(id);
        LocalDate transactionLocalDateValidRange = transaction.getCreatedDate().minusMonths(6);

        FilterDto mockedDto = new FilterDto(
                country,
                currency,
                countryCurrency,
                transactionLocalDateValidRange);
        List<RatesDto> ratesList = ratesExchangeService.fetchRates(mockedDto);
        if (ratesList.isEmpty()) {
            throw new NoRateAvailableException();
        }
        RatesDto firstRate = ratesList.get(0);

        BigDecimal convertedExchangeRate = EvaluateExchangeRate.evaluateExchangeRate(firstRate, transaction);

        TransactionWithRate response = new TransactionWithRate(transaction, firstRate, convertedExchangeRate);

        return ResponseEntity.ok().body(response);

    }

}
