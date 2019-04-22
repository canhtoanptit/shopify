package com.quyenbeo.shopifym.service.impl;

import com.quyenbeo.shopifym.service.ShopInfoService;
import com.quyenbeo.shopifym.domain.ShopInfo;
import com.quyenbeo.shopifym.repository.ShopInfoRepository;
import com.quyenbeo.shopifym.service.dto.ShopInfoDTO;
import com.quyenbeo.shopifym.service.mapper.ShopInfoMapper;
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
public class ShopInfoServiceImpl implements ShopInfoService {

    private final Logger log = LoggerFactory.getLogger(ShopInfoServiceImpl.class);

    private final ShopInfoRepository shopInfoRepository;

    private final ShopInfoMapper shopInfoMapper;

    public ShopInfoServiceImpl(ShopInfoRepository shopInfoRepository, ShopInfoMapper shopInfoMapper) {
        this.shopInfoRepository = shopInfoRepository;
        this.shopInfoMapper = shopInfoMapper;
    }

    /**
     * Save a shopInfo.
     *
     * @param shopInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShopInfoDTO save(ShopInfoDTO shopInfoDTO) {
        log.debug("Request to save ShopInfo : {}", shopInfoDTO);
        ShopInfo shopInfo = shopInfoMapper.toEntity(shopInfoDTO);
        shopInfo = shopInfoRepository.save(shopInfo);
        return shopInfoMapper.toDto(shopInfo);
    }

    /**
     * Get all the shopInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShopInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShopInfos");
        return shopInfoRepository.findAll(pageable)
            .map(shopInfoMapper::toDto);
    }

    /**
     * Get all the ShopInfo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ShopInfoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return shopInfoRepository.findAllWithEagerRelationships(pageable).map(shopInfoMapper::toDto);
    }
    

    /**
     * Get one shopInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShopInfoDTO> findOne(Long id) {
        log.debug("Request to get ShopInfo : {}", id);
        return shopInfoRepository.findOneWithEagerRelationships(id)
            .map(shopInfoMapper::toDto);
    }

    /**
     * Delete the shopInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShopInfo : {}", id);        shopInfoRepository.deleteById(id);
    }
}
