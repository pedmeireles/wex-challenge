package com.example.wex_challenge.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wex_challenge.ratesExchange.FilterDto;
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

    @GetMapping("/test")
    public ResponseEntity<List<RatesDto>> testFetchingFromFiscalData() {
        FilterDto mockedDto = new FilterDto(
            "Australia",
            "Dollar",
            "Australia-Dollar",
            LocalDate.of(2026, 1, 1)
        );

        return ResponseEntity.ok().body(ratesExchangeService.fetchRates(mockedDto));

    }

}
