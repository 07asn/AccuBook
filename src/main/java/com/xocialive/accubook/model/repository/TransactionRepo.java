package com.xocialive.accubook.model.repository;

import com.xocialive.accubook.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByClientId(Long clientId);
}
