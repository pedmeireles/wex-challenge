package com.example.wex_challenge;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.wex_challenge.ratesExchange.FilterWithoutCurrencyOrCountryException;
import com.example.wex_challenge.ratesExchange.FilterWithoutTransactionDateException;
import com.example.wex_challenge.ratesExchange.NoRateAvailableException;
import com.example.wex_challenge.transaction.TransactionNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            TransactionNotFoundException.class,
            FilterWithoutTransactionDateException.class,
            FilterWithoutCurrencyOrCountryException.class,
            NoRateAvailableException.class
    })
    public ResponseEntity<String> handleTransactionNotFound(RuntimeException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
