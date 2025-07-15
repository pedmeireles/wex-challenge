package com.example.wex_challenge.transaction;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/transaction")
public class TransactionController {

    public final TransactionService service;

    public TransactionController(TransactionService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody @Valid TransactionDto dto) {
        return ResponseEntity.ok().body(service.createTransaction(dto));
        
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok().body(service.getAllTransactions());
    }
    // @GetMapping
    // public ResponseEntity<List<TransactionDto>> getAllTransactions() {
    //     return ResponseEntity.ok(service.getAllTransactions());
    // }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getTransactionById(id));
    }
    
    
    
}
