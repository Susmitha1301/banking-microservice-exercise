package com.example.transaction_service.repository;

import com.example.transaction_service.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByFromAccountNumberOrToAccountNumber(
            String fromAccountNumber,
            String toAccountNumber,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM transactions WHERE amount >= :amount",
            nativeQuery = true
    )
    List<Transaction> findHighValueTransactions(@Param("amount") BigDecimal amount);
}