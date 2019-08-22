package com.paypal.mng.service.impl;

import com.paypal.mng.service.TrackingService;
import com.paypal.mng.domain.Tracking;
import com.paypal.mng.repository.TrackingRepository;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.mapper.TrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tracking}.
 */
@Service
@Transactional
public class TrackingServiceImpl implements TrackingService {

    private final Logger log = LoggerFactory.getLogger(TrackingServiceImpl.class);

    private final TrackingRepository trackingRepository;

    private final TrackingMapper trackingMapper;

    public TrackingServiceImpl(TrackingRepository trackingRepository, TrackingMapper trackingMapper) {
        this.trackingRepository = trackingRepository;
        this.trackingMapper = trackingMapper;
    }

    /**
     * Save a tracking.
     *
     * @param trackingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrackingDTO save(TrackingDTO trackingDTO) {
        log.debug("Request to save Tracking : {}", trackingDTO);
        Tracking tracking = trackingMapper.toEntity(trackingDTO);
        tracking = trackingRepository.save(tracking);
        return trackingMapper.toDto(tracking);
    }

    /**
     * Get all the trackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrackingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trackings");
        return trackingRepository.findAll(pageable)
            .map(TrackingDTO::toDto);
    }


    /**
     * Get one tracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrackingDTO> findOne(Long id) {
        log.debug("Request to get Tracking : {}", id);
        return trackingRepository.findById(id)
            .map(trackingMapper::toDto);
    }

    /**
     * Delete the tracking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tracking : {}", id);
        trackingRepository.deleteById(id);
    }

    @Override
    public Optional<TrackingDTO> findByTrackingNumber(String trackingNumber) {
        return trackingRepository.findByTrackingNumber(trackingNumber)
            .map(trackingMapper::toDto);
    }

    @Override
    public Optional<Tracking> findByOrderNameAndTrackingNumber(String orderName, String trackingNumber) {
        return trackingRepository.findByOrderNameAndTrackingNumber(orderName, trackingNumber);
    }
}
