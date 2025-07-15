package com.example.wex_challenge.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
