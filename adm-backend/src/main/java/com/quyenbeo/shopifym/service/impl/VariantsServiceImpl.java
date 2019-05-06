package com.quyenbeo.shopifym.service.impl;

import com.quyenbeo.shopifym.service.VariantsService;
import com.quyenbeo.shopifym.domain.Variants;
import com.quyenbeo.shopifym.repository.VariantsRepository;
import com.quyenbeo.shopifym.service.dto.VariantsDTO;
import com.quyenbeo.shopifym.service.mapper.VariantsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Variants.
 */
@Service
@Transactional
public class VariantsServiceImpl implements VariantsService {

    private final Logger log = LoggerFactory.getLogger(VariantsServiceImpl.class);

    private final VariantsRepository variantsRepository;

    private final VariantsMapper variantsMapper;

    public VariantsServiceImpl(VariantsRepository variantsRepository, VariantsMapper variantsMapper) {
        this.variantsRepository = variantsRepository;
        this.variantsMapper = variantsMapper;
    }

    /**
     * Save a variants.
     *
     * @param variantsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VariantsDTO save(VariantsDTO variantsDTO) {
        log.debug("Request to save Variants : {}", variantsDTO);
        Variants variants = variantsMapper.toEntity(variantsDTO);
        variants = variantsRepository.save(variants);
        return variantsMapper.toDto(variants);
    }

    /**
     * Get all the variants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VariantsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variants");
        return variantsRepository.findAll(pageable)
            .map(variantsMapper::toDto);
    }


    /**
     * Get one variants by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VariantsDTO> findOne(Long id) {
        log.debug("Request to get Variants : {}", id);
        return variantsRepository.findById(id)
            .map(variantsMapper::toDto);
    }

    /**
     * Delete the variants by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Variants : {}", id);        variantsRepository.deleteById(id);
    }
}
