package com.paypal.mng.service;

import com.paypal.mng.service.dto.PaypalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.paypal.mng.domain.Paypal}.
 */
public interface PaypalService {

    /**
     * Save a paypal.
     *
     * @param paypalDTO the entity to save.
     * @return the persisted entity.
     */
    PaypalDTO save(PaypalDTO paypalDTO);

    /**
     * Get all the paypals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaypalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paypal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaypalDTO> findOne(Long id);

    /**
     * Delete the "id" paypal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
