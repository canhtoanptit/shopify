package com.quyenbeo.shopifym.service;

import com.quyenbeo.shopifym.service.dto.ShopinfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShopInfo.
 */
public interface ShopinfoService {

    /**
     * Save a shopinfo.
     *
     * @param shopinfoDTO the entity to save
     * @return the persisted entity
     */
    ShopinfoDTO save(ShopinfoDTO shopinfoDTO);

    /**
     * Get all the shopinfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShopinfoDTO> findAll(Pageable pageable);

    /**
     * Get all the ShopInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ShopinfoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" shopinfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShopinfoDTO> findOne(Long id);

    /**
     * Delete the "id" shopinfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
