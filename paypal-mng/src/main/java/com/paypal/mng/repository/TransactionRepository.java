package com.paypal.mng.repository;

import com.paypal.mng.domain.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByShopifyTransactionIdAndOrderId(Long shopifyTransactionId, Long OrderId);
}
