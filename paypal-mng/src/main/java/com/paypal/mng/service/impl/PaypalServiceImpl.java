package com.paypal.mng.service.impl;

import com.paypal.mng.service.PaypalService;
import com.paypal.mng.domain.Paypal;
import com.paypal.mng.repository.PaypalRepository;
import com.paypal.mng.service.dto.PaypalDTO;
import com.paypal.mng.service.mapper.PaypalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Paypal}.
 */
@Service
@Transactional
public class PaypalServiceImpl implements PaypalService {

    private final Logger log = LoggerFactory.getLogger(PaypalServiceImpl.class);

    private final PaypalRepository paypalRepository;

    private final PaypalMapper paypalMapper;

    public PaypalServiceImpl(PaypalRepository paypalRepository, PaypalMapper paypalMapper) {
        this.paypalRepository = paypalRepository;
        this.paypalMapper = paypalMapper;
    }

    /**
     * Save a paypal.
     *
     * @param paypalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaypalDTO save(PaypalDTO paypalDTO) {
        log.debug("Request to save Paypal : {}", paypalDTO);
        Paypal paypal = paypalMapper.toEntity(paypalDTO);
        paypal = paypalRepository.save(paypal);
        return paypalMapper.toDto(paypal);
    }

    /**
     * Get all the paypals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaypalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paypals");
        return paypalRepository.findAll(pageable)
            .map(paypalMapper::toDto);
    }


    /**
     * Get one paypal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaypalDTO> findOne(Long id) {
        log.debug("Request to get Paypal : {}", id);
        return paypalRepository.findById(id)
            .map(paypalMapper::toDto);
    }

    /**
     * Delete the paypal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paypal : {}", id);
        paypalRepository.deleteById(id);
    }
}
