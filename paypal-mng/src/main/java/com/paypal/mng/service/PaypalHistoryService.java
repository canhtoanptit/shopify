package com.paypal.mng.service;

import com.paypal.mng.service.dto.PaypalHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.paypal.mng.domain.PaypalHistory}.
 */
public interface PaypalHistoryService {

    /**
     * Save a paypalHistory.
     *
     * @param paypalHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    PaypalHistoryDTO save(PaypalHistoryDTO paypalHistoryDTO);

    /**
     * Get all the paypalHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaypalHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paypalHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaypalHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" paypalHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<PaypalHistoryDTO> findByTransactionIdAndTrackingNumber(String shopifyAuthorizationKey, String shopifyTrackingNumber);
}
