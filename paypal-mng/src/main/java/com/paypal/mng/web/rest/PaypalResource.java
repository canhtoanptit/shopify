package com.paypal.mng.web.rest;

import com.paypal.mng.security.AuthoritiesConstants;
import com.paypal.mng.service.PaypalService;
import com.paypal.mng.web.rest.errors.BadRequestAlertException;
import com.paypal.mng.service.dto.PaypalDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.paypal.mng.domain.Paypal}.
 */
@RestController
@RequestMapping("/api")
public class PaypalResource {

    private final Logger log = LoggerFactory.getLogger(PaypalResource.class);

    private static final String ENTITY_NAME = "paypal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaypalService paypalService;

    public PaypalResource(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    /**
     * {@code POST  /paypals} : Create a new paypal.
     *
     * @param paypalDTO the paypalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paypalDTO, or with status {@code 400 (Bad Request)} if the paypal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paypals")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PaypalDTO> createPaypal(@Valid @RequestBody PaypalDTO paypalDTO) throws URISyntaxException {
        log.debug("REST request to save Paypal : {}", paypalDTO);
        if (paypalDTO.getId() != null) {
            throw new BadRequestAlertException("A new paypal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaypalDTO result = paypalService.save(paypalDTO);
        return ResponseEntity.created(new URI("/api/paypals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paypals} : Updates an existing paypal.
     *
     * @param paypalDTO the paypalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paypalDTO,
     * or with status {@code 400 (Bad Request)} if the paypalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paypalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paypals")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PaypalDTO> updatePaypal(@Valid @RequestBody PaypalDTO paypalDTO) throws URISyntaxException {
        log.debug("REST request to update Paypal : {}", paypalDTO);
        if (paypalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaypalDTO result = paypalService.save(paypalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paypalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paypals} : get all the paypals.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paypals in body.
     */
    @GetMapping("/paypals")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<PaypalDTO>> getAllPaypals(Pageable pageable) {
        log.debug("REST request to get a page of Paypals");
        Page<PaypalDTO> page = paypalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paypals/:id} : get the "id" paypal.
     *
     * @param id the id of the paypalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paypalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paypals/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PaypalDTO> getPaypal(@PathVariable Long id) {
        log.debug("REST request to get Paypal : {}", id);
        Optional<PaypalDTO> paypalDTO = paypalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paypalDTO);
    }

    /**
     * {@code DELETE  /paypals/:id} : delete the "id" paypal.
     *
     * @param id the id of the paypalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paypals/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deletePaypal(@PathVariable Long id) {
        log.debug("REST request to delete Paypal : {}", id);
        paypalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
