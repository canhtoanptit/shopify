package com.quyenbeo.shopifym.web.rest;
import com.quyenbeo.shopifym.service.ShopinfoService;
import com.quyenbeo.shopifym.web.rest.errors.BadRequestAlertException;
import com.quyenbeo.shopifym.web.rest.util.HeaderUtil;
import com.quyenbeo.shopifym.web.rest.util.PaginationUtil;
import com.quyenbeo.shopifym.service.dto.ShopinfoDTO;
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
public class ShopinfoResource {

    private final Logger log = LoggerFactory.getLogger(ShopinfoResource.class);

    private static final String ENTITY_NAME = "shopinfo";

    private final ShopinfoService shopinfoService;

    public ShopinfoResource(ShopinfoService shopinfoService) {
        this.shopinfoService = shopinfoService;
    }

    /**
     * POST  /shopinfos : Create a new shopinfo.
     *
     * @param shopinfoDTO the shopinfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shopinfoDTO, or with status 400 (Bad Request) if the shopinfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shopinfos")
    public ResponseEntity<ShopinfoDTO> createShopinfo(@Valid @RequestBody ShopinfoDTO shopinfoDTO) throws URISyntaxException {
        log.debug("REST request to save ShopInfo : {}", shopinfoDTO);
        if (shopinfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShopinfoDTO result = shopinfoService.save(shopinfoDTO);
        return ResponseEntity.created(new URI("/api/shopinfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shopinfos : Updates an existing shopinfo.
     *
     * @param shopinfoDTO the shopinfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shopinfoDTO,
     * or with status 400 (Bad Request) if the shopinfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the shopinfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shopinfos")
    public ResponseEntity<ShopinfoDTO> updateShopinfo(@Valid @RequestBody ShopinfoDTO shopinfoDTO) throws URISyntaxException {
        log.debug("REST request to update ShopInfo : {}", shopinfoDTO);
        if (shopinfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShopinfoDTO result = shopinfoService.save(shopinfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shopinfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shopinfos : get all the shopinfos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of shopinfos in body
     */
    @GetMapping("/shopinfos")
    public ResponseEntity<List<ShopinfoDTO>> getAllShopinfos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Shopinfos");
        Page<ShopinfoDTO> page;
        if (eagerload) {
            page = shopinfoService.findAllWithEagerRelationships(pageable);
        } else {
            page = shopinfoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/shopinfos?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shopinfos/:id : get the "id" shopinfo.
     *
     * @param id the id of the shopinfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shopinfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shopinfos/{id}")
    public ResponseEntity<ShopinfoDTO> getShopinfo(@PathVariable Long id) {
        log.debug("REST request to get ShopInfo : {}", id);
        Optional<ShopinfoDTO> shopinfoDTO = shopinfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shopinfoDTO);
    }

    /**
     * DELETE  /shopinfos/:id : delete the "id" shopinfo.
     *
     * @param id the id of the shopinfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shopinfos/{id}")
    public ResponseEntity<Void> deleteShopinfo(@PathVariable Long id) {
        log.debug("REST request to delete ShopInfo : {}", id);
        shopinfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
