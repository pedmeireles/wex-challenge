package com.example.wex_challenge.transaction;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }

    
    public TransactionDto createTransaction(TransactionDto dto){

        LocalDate createdDate = (dto.getCreatedDate() == null) ? 
        LocalDate.now(ZoneOffset.UTC) : dto.getCreatedDate();

        Transaction transaction = Transaction.builder()
        .createdDate(createdDate)
        .description(dto.getDescription())
        .amount(dto.getAmount())
        .build();

        Transaction saved = repository.save(transaction);
        return TransactionDto.builder()
        .id(saved.getId())
        .description(saved.getDescription())
        .createdDate(saved.getCreatedDate())
        .amount(saved.getAmount())
        .build();
    }
    
    public TransactionDto getTransactionById(Long id){
        Transaction transaction = repository.findById(id)
        .orElseThrow(() -> new TransactionNotFoundException(id));

        return new TransactionDto(
            transaction.getId(),
            transaction.getDescription(),
            transaction.getCreatedDate(),
            transaction.getAmount()
        );
    }
    public List<TransactionDto> getAllTransactions(){
        return repository.findAll().stream().map(transaction -> new TransactionDto(
             transaction.getId(),
            transaction.getDescription(),
            transaction.getCreatedDate(),
            transaction.getAmount()
        )).toList();
    }
}
