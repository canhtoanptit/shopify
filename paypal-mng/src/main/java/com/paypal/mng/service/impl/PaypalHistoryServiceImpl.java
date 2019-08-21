package com.paypal.mng.service.impl;

import com.paypal.mng.service.PaypalHistoryService;
import com.paypal.mng.domain.PaypalHistory;
import com.paypal.mng.repository.PaypalHistoryRepository;
import com.paypal.mng.service.dto.PaypalHistoryDTO;
import com.paypal.mng.service.mapper.PaypalHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaypalHistory}.
 */
@Service
@Transactional
public class PaypalHistoryServiceImpl implements PaypalHistoryService {

    private final Logger log = LoggerFactory.getLogger(PaypalHistoryServiceImpl.class);

    private final PaypalHistoryRepository paypalHistoryRepository;

    private final PaypalHistoryMapper paypalHistoryMapper;

    public PaypalHistoryServiceImpl(PaypalHistoryRepository paypalHistoryRepository, PaypalHistoryMapper paypalHistoryMapper) {
        this.paypalHistoryRepository = paypalHistoryRepository;
        this.paypalHistoryMapper = paypalHistoryMapper;
    }

    /**
     * Save a paypalHistory.
     *
     * @param paypalHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaypalHistoryDTO save(PaypalHistoryDTO paypalHistoryDTO) {
        log.debug("Request to save PaypalHistory : {}", paypalHistoryDTO);
        PaypalHistory paypalHistory = paypalHistoryMapper.toEntity(paypalHistoryDTO);
        paypalHistory = paypalHistoryRepository.save(paypalHistory);
        return paypalHistoryMapper.toDto(paypalHistory);
    }

    /**
     * Get all the paypalHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaypalHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaypalHistories");
        return paypalHistoryRepository.findAll(pageable)
            .map(paypalHistoryMapper::toDto);
    }


    /**
     * Get one paypalHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaypalHistoryDTO> findOne(Long id) {
        log.debug("Request to get PaypalHistory : {}", id);
        return paypalHistoryRepository.findById(id)
            .map(paypalHistoryMapper::toDto);
    }

    /**
     * Delete the paypalHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaypalHistory : {}", id);
        paypalHistoryRepository.deleteById(id);
    }

    @Override
    public Optional<PaypalHistoryDTO> findByTransactionIdAndTrackingNumber(String shopifyAuthorizationKey, String shopifyTrackingNumber) {
        return paypalHistoryRepository.findByShopifyAuthorizationKeyAndShopifyTrackingNumber(shopifyAuthorizationKey, shopifyTrackingNumber)
            .map(paypalHistoryMapper::toDto);
    }

    /**
     * findByOrderIdAndTrackingNumber
     *
     * @param shopifyOrderId
     * @param shopifyTrackingNumber
     * @return
     */
    @Override
    public Optional<PaypalHistoryDTO> findByOrderIdAndTrackingNumber(Long shopifyOrderId, String shopifyTrackingNumber) {
        return paypalHistoryRepository.findByShopifyOrderIdAndShopifyTrackingNumber(shopifyOrderId, shopifyTrackingNumber)
            .map(paypalHistoryMapper::toDto);
    }
}
