package com.paypal.mng.repository;

import com.paypal.mng.domain.PaypalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PaypalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaypalHistoryRepository extends JpaRepository<PaypalHistory, Long> {
    List<PaypalHistory> findByShopifyAuthorizationKeyAndShopifyTrackingNumber(String transactionId, String trackingNumber);

    Optional<PaypalHistory> findByShopifyOrderIdAndShopifyTrackingNumber(Long shopifyOrderId, String trackingNumber);

    Page<PaypalHistory> findAllByShopifyOrderIdIn(List<Long> shopifyOrderIds, Pageable pageable);

    Page<PaypalHistory> findAllByShopifyAuthorizationKey(String shopifyAuthorizationKey, Pageable pageable);

    Optional<PaypalHistory> findByShopifyOrderName(String orderName);

    List<PaypalHistory> findAllByStatus(Integer status);
}
