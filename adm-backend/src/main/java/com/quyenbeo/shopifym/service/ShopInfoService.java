package com.quyenbeo.shopifym.service;

import com.quyenbeo.shopifym.service.dto.ShopInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShopInfo.
 */
public interface ShopInfoService {

    /**
     * Save a shopInfo.
     *
     * @param shopInfoDTO the entity to save
     * @return the persisted entity
     */
    ShopInfoDTO save(ShopInfoDTO shopInfoDTO);

    /**
     * Get all the shopInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShopInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the ShopInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ShopInfoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" shopInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShopInfoDTO> findOne(Long id);

    /**
     * Delete the "id" shopInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
