package com.quyenbeo.shopifym.web.rest;
import com.quyenbeo.shopifym.service.VariantsService;
import com.quyenbeo.shopifym.web.rest.errors.BadRequestAlertException;
import com.quyenbeo.shopifym.web.rest.util.HeaderUtil;
import com.quyenbeo.shopifym.web.rest.util.PaginationUtil;
import com.quyenbeo.shopifym.service.dto.VariantsDTO;
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
 * REST controller for managing Variants.
 */
@RestController
@RequestMapping("/api")
public class VariantsResource {

    private final Logger log = LoggerFactory.getLogger(VariantsResource.class);

    private static final String ENTITY_NAME = "variants";

    private final VariantsService variantsService;

    public VariantsResource(VariantsService variantsService) {
        this.variantsService = variantsService;
    }

    /**
     * POST  /variants : Create a new variants.
     *
     * @param variantsDTO the variantsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variantsDTO, or with status 400 (Bad Request) if the variants has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/variants")
    public ResponseEntity<VariantsDTO> createVariants(@Valid @RequestBody VariantsDTO variantsDTO) throws URISyntaxException {
        log.debug("REST request to save Variants : {}", variantsDTO);
        if (variantsDTO.getId() != null) {
            throw new BadRequestAlertException("A new variants cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariantsDTO result = variantsService.save(variantsDTO);
        return ResponseEntity.created(new URI("/api/variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variants : Updates an existing variants.
     *
     * @param variantsDTO the variantsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variantsDTO,
     * or with status 400 (Bad Request) if the variantsDTO is not valid,
     * or with status 500 (Internal Server Error) if the variantsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/variants")
    public ResponseEntity<VariantsDTO> updateVariants(@Valid @RequestBody VariantsDTO variantsDTO) throws URISyntaxException {
        log.debug("REST request to update Variants : {}", variantsDTO);
        if (variantsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariantsDTO result = variantsService.save(variantsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variantsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variants : get all the variants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of variants in body
     */
    @GetMapping("/variants")
    public ResponseEntity<List<VariantsDTO>> getAllVariants(Pageable pageable) {
        log.debug("REST request to get a page of Variants");
        Page<VariantsDTO> page = variantsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/variants");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /variants/:id : get the "id" variants.
     *
     * @param id the id of the variantsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variantsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/variants/{id}")
    public ResponseEntity<VariantsDTO> getVariants(@PathVariable Long id) {
        log.debug("REST request to get Variants : {}", id);
        Optional<VariantsDTO> variantsDTO = variantsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variantsDTO);
    }

    /**
     * DELETE  /variants/:id : delete the "id" variants.
     *
     * @param id the id of the variantsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/variants/{id}")
    public ResponseEntity<Void> deleteVariants(@PathVariable Long id) {
        log.debug("REST request to delete Variants : {}", id);
        variantsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
