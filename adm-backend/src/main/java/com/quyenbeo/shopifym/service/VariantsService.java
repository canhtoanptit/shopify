package com.quyenbeo.shopifym.service;

import com.quyenbeo.shopifym.service.dto.VariantsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Variants.
 */
public interface VariantsService {

    /**
     * Save a variants.
     *
     * @param variantsDTO the entity to save
     * @return the persisted entity
     */
    VariantsDTO save(VariantsDTO variantsDTO);

    /**
     * Get all the variants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VariantsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" variants.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VariantsDTO> findOne(Long id);

    /**
     * Delete the "id" variants.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
