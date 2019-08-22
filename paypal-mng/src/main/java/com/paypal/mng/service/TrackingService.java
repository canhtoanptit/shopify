package com.paypal.mng.service;

import com.paypal.mng.domain.Tracking;
import com.paypal.mng.service.dto.TrackingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.paypal.mng.domain.Tracking}.
 */
public interface TrackingService {

    /**
     * Save a tracking.
     *
     * @param trackingDTO the entity to save.
     * @return the persisted entity.
     */
    TrackingDTO save(TrackingDTO trackingDTO);

    /**
     * Get all the trackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrackingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tracking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrackingDTO> findOne(Long id);

    /**
     * Delete the "id" tracking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * find tracking by tracking_number
     *
     * @param trackingNumber
     * @return
     */
    Optional<TrackingDTO> findByTrackingNumber(String trackingNumber);

    Optional<Tracking> findByOrderNameAndTrackingNumber(String orderName,String trackingNumber);
}
