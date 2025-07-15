package com.example.wex_challenge.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    private TransactionDto transactionDto;

    @BeforeEach
    public void setup(){
        transactionDto = TransactionDto.builder()
        .amount(new BigDecimal("1.00"))
        .description("A description")
        .createdDate(LocalDate.of(2025,1,1))
        .build();
    }

    @Test
    public void saveTransactionTest(){
        
        
        //precondition
        Transaction transaction = Transaction.builder()
        .amount(new BigDecimal("1.00"))
        .description("A description")
        .createdDate(LocalDate.of(2025,1,1))
        .build();
        Transaction transactionWithId = new Transaction(1l, transaction.getDescription(),transaction.getCreatedDate(),transaction.getAmount());

        given(repository.save(transaction)).willReturn(transactionWithId);

        //action
        TransactionDto savedTransaction = service.createTransaction(transactionDto);

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualTo(new BigDecimal("1.00"));
        assertThat(savedTransaction.getCreatedDate()).isEqualTo(LocalDate.of(2025,1,1));
        assertThat(savedTransaction.getDescription()).isEqualTo("A description");
        assertThat(savedTransaction.getId()).isEqualTo(1L);
    }
    
    
    @Test
    public void saveTransactionWithoutCreatedDateTest(){
        
        
        //precondition
        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        Transaction transaction = Transaction.builder()
        .amount(new BigDecimal("1.00"))
        .description("A description")
        .createdDate(now)
        .build();


        Transaction transactionWithId = new Transaction(1l, transaction.getDescription(),transaction.getCreatedDate(),transaction.getAmount());

        TransactionDto transactionDtoWithoutDate = transactionDto.builder()
        .amount(transactionDto.getAmount())
        .description(transactionDto.getDescription())
        .build()        ;
        given(repository.save(transaction)).willReturn(transactionWithId);

        //action
        TransactionDto savedTransaction = service.createTransaction(transactionDtoWithoutDate);

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualTo(new BigDecimal("1.00"));
        assertThat(savedTransaction.getCreatedDate()).isEqualTo(now);
        assertThat(savedTransaction.getDescription()).isEqualTo("A description");
        assertThat(savedTransaction.getId()).isEqualTo(1L);
    }
    
    
    @Test
    public void getAllTransactionsWithSingleItem(){
        
        
        //precondition
        Transaction transaction = Transaction.builder()
        .amount(new BigDecimal("1.00"))
        .description("A description")
        .createdDate(LocalDate.of(2025,1,1))
        .id(1L)
        .build();


        given(repository.findAll()).willReturn(List.of(transaction));

        //action
        List<TransactionDto> savedTransaction = service.getAllTransactions();

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction).hasSize(1);
        assertThat(savedTransaction.get(0).getAmount()).isEqualTo(new BigDecimal("1.00"));
        assertThat(savedTransaction.get(0).getCreatedDate()).isEqualTo(LocalDate.of(2025,1,1));
        assertThat(savedTransaction.get(0).getDescription()).isEqualTo("A description");
        assertThat(savedTransaction.get(0).getId()).isEqualTo(1L);
    }
    
    @Test
    public void getAllTransactionsWithMultipleItems(){
        
        
        //precondition
        Transaction transaction = Transaction.builder()
        .amount(new BigDecimal("1.00"))
        .description("A description")
        .createdDate(LocalDate.of(2025,1,1))
        .id(1L)
        .build();


        given(repository.findAll()).willReturn(List.of(transaction, transaction));

        //action
        List<TransactionDto> savedTransaction = service.getAllTransactions();

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction).hasSize(2);
    }
    
    @Test
    public void getAllTransactionsWithNoItems(){
        
        
        //precondition
        given(repository.findAll()).willReturn(List.of());

        //action
        List<TransactionDto> savedTransaction = service.getAllTransactions();

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction).hasSize(0);
    }

    @Test
    public void getATransactionByIdSuccess(){

        //Precondition

        Transaction buildedTransaction = Transaction.builder()
            .amount(new BigDecimal("1.00"))
            .description("A description")
            .createdDate(LocalDate.of(2025,1,1))
            .id(1L)
            .build();

        Optional<Transaction> transaction = Optional.of(buildedTransaction);
        given(repository.findById(1L)).willReturn(transaction);

        //action

        TransactionDto transactionId = service.getTransactionById(1L);

        //assertion
        assertThat(transactionId).isNotNull();
        assertThat(transactionId.getAmount()).isEqualTo(buildedTransaction.getAmount());
        assertThat(transactionId.getDescription()).isEqualTo(buildedTransaction.getDescription());
        assertThat(transactionId.getCreatedDate()).isEqualTo(buildedTransaction.getCreatedDate());
        assertThat(transactionId.getId()).isEqualTo(buildedTransaction.getId());
    }
    


}
