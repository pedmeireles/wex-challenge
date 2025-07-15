package com.example.wex_challenge.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    
    @Null
    private Long id;

    @Size(max=50, message = "description must have at most 50 characters")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @PastOrPresent(message = "A transaction cannot happen on the future.")
    private LocalDate createdDate;

    @Column(precision = 15, scale = 2)
    @NotNull(message="amount is required")
    @DecimalMin(value="0.01",  message = "amount must be at least 1 cent")
    private BigDecimal amount;

    
}
