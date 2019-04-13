package com.quyenbeo.shopifym.service.impl;

import com.quyenbeo.shopifym.service.ShopinfoService;
import com.quyenbeo.shopifym.domain.ShopInfo;
import com.quyenbeo.shopifym.repository.ShopinfoRepository;
import com.quyenbeo.shopifym.service.dto.ShopinfoDTO;
import com.quyenbeo.shopifym.service.mapper.ShopinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ShopInfo.
 */
@Service
@Transactional
public class ShopinfoServiceImpl implements ShopinfoService {

    private final Logger log = LoggerFactory.getLogger(ShopinfoServiceImpl.class);

    private final ShopinfoRepository shopinfoRepository;

    private final ShopinfoMapper shopinfoMapper;

    public ShopinfoServiceImpl(ShopinfoRepository shopinfoRepository, ShopinfoMapper shopinfoMapper) {
        this.shopinfoRepository = shopinfoRepository;
        this.shopinfoMapper = shopinfoMapper;
    }

    /**
     * Save a shopinfo.
     *
     * @param shopinfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShopinfoDTO save(ShopinfoDTO shopinfoDTO) {
        log.debug("Request to save ShopInfo : {}", shopinfoDTO);
        ShopInfo shopinfo = shopinfoMapper.toEntity(shopinfoDTO);
        shopinfo = shopinfoRepository.save(shopinfo);
        return shopinfoMapper.toDto(shopinfo);
    }

    /**
     * Get all the shopinfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShopinfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shopinfos");
        return shopinfoRepository.findAll(pageable)
            .map(shopinfoMapper::toDto);
    }

    /**
     * Get all the ShopInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ShopinfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return shopinfoRepository.findAllWithEagerRelationships(pageable).map(shopinfoMapper::toDto);
    }
    

    /**
     * Get one shopinfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShopinfoDTO> findOne(Long id) {
        log.debug("Request to get ShopInfo : {}", id);
        return shopinfoRepository.findOneWithEagerRelationships(id)
            .map(shopinfoMapper::toDto);
    }

    /**
     * Delete the shopinfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShopInfo : {}", id);        shopinfoRepository.deleteById(id);
    }
}
