package com.xocialive.accubook.model.repository;

import com.xocialive.accubook.model.entity.Transaction;
import com.xocialive.accubook.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByClientId(Long clientId);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByClientIdAndType(Long client_id, TransactionType type);

    @Query(value = "SELECT SUM(amount) FROM transactions t WHERE t.client_id = :clientId AND t.type = 'RECEIVED'", nativeQuery = true)
    BigDecimal sumMoneyReceivedByClient(Long clientId);

    @Query(value = "SELECT SUM(amount) FROM transactions t WHERE t.client_id = :clientId AND t.type = 'BORROWED'", nativeQuery = true)
    BigDecimal sumMoneyBorrowedByClient(Long clientId);

    @Query(value = "SELECT SUM(t.amount) FROM transactions t INNER JOIN clients c ON t.client_id = c.id WHERE c.user_id = :userId AND t.type = 'RECEIVED'", nativeQuery = true)
    BigDecimal sumMoneyReceivedByAllClients(@Param("userId") Long userId);

    @Query(value = "SELECT SUM(t.amount) FROM transactions t INNER JOIN clients c ON t.client_id = c.id WHERE c.user_id = :userId AND t.type = 'BORROWED'", nativeQuery = true)
    BigDecimal sumMoneyBorrowedByAllClients(@Param("userId") Long userId);

}
