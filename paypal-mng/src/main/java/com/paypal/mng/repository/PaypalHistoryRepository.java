package com.paypal.mng.repository;

import com.paypal.mng.domain.PaypalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the PaypalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaypalHistoryRepository extends JpaRepository<PaypalHistory, Long> {
    Optional<PaypalHistory> findByShopifyAuthorizationKeyAndShopifyTrackingNumber(String transactionId, String trackingNumber);

    Optional<PaypalHistory> findByShopifyOrderIdAndShopifyTrackingNumber(Long shopifyOrderId, String trackingNumber);

    Page<PaypalHistory> findAllByShopifyOrderId(Long shopifyOrderId, Pageable pageable);

    Page<PaypalHistory> findAllByShopifyAuthorizationKey(String shopifyAuthorizationKey, Pageable pageable);
}
