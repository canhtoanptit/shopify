package com.quyenbeo.shopifym.web.rest;
import com.quyenbeo.shopifym.service.ShopInfoService;
import com.quyenbeo.shopifym.web.rest.errors.BadRequestAlertException;
import com.quyenbeo.shopifym.web.rest.util.HeaderUtil;
import com.quyenbeo.shopifym.web.rest.util.PaginationUtil;
import com.quyenbeo.shopifym.service.dto.ShopInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ShopInfo.
 */
@RestController
@RequestMapping("/api")
public class ShopInfoResource {

    private final Logger log = LoggerFactory.getLogger(ShopInfoResource.class);

    private static final String ENTITY_NAME = "shopInfo";

    private final ShopInfoService shopInfoService;

    public ShopInfoResource(ShopInfoService shopInfoService) {
        this.shopInfoService = shopInfoService;
    }

    /**
     * POST  /shop-infos : Create a new shopInfo.
     *
     * @param shopInfoDTO the shopInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shopInfoDTO, or with status 400 (Bad Request) if the shopInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shop-infos")
    public ResponseEntity<ShopInfoDTO> createShopInfo(@Valid @RequestBody ShopInfoDTO shopInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ShopInfo : {}", shopInfoDTO);
        if (shopInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity.created(new URI("/api/shop-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shop-infos : Updates an existing shopInfo.
     *
     * @param shopInfoDTO the shopInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shopInfoDTO,
     * or with status 400 (Bad Request) if the shopInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the shopInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shop-infos")
    public ResponseEntity<ShopInfoDTO> updateShopInfo(@Valid @RequestBody ShopInfoDTO shopInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ShopInfo : {}", shopInfoDTO);
        if (shopInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shopInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shop-infos : get all the shopInfos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of shopInfos in body
     */
    @GetMapping("/shop-infos")
    public ResponseEntity<List<ShopInfoDTO>> getAllShopInfos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ShopInfos");
        Page<ShopInfoDTO> page;
        if (eagerload) {
            page = shopInfoService.findAllWithEagerRelationships(pageable);
        } else {
            page = shopInfoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/shop-infos?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shop-infos/:id : get the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shopInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shop-infos/{id}")
    public ResponseEntity<ShopInfoDTO> getShopInfo(@PathVariable Long id) {
        log.debug("REST request to get ShopInfo : {}", id);
        Optional<ShopInfoDTO> shopInfoDTO = shopInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shopInfoDTO);
    }

    /**
     * DELETE  /shop-infos/:id : delete the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shop-infos/{id}")
    public ResponseEntity<Void> deleteShopInfo(@PathVariable Long id) {
        log.debug("REST request to delete ShopInfo : {}", id);
        shopInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
