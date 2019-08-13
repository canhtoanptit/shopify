package com.paypal.mng.web.rest;

import com.paypal.mng.service.TrackingService;
import com.paypal.mng.web.rest.errors.BadRequestAlertException;
import com.paypal.mng.service.dto.TrackingDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.paypal.mng.domain.Tracking}.
 */
@RestController
@RequestMapping("/api")
public class TrackingResource {

    private final Logger log = LoggerFactory.getLogger(TrackingResource.class);

    private static final String ENTITY_NAME = "tracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrackingService trackingService;

    public TrackingResource(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    /**
     * {@code POST  /trackings} : Create a new tracking.
     *
     * @param trackingDTO the trackingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trackingDTO, or with status {@code 400 (Bad Request)} if the tracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trackings")
    public ResponseEntity<TrackingDTO> createTracking(@Valid @RequestBody TrackingDTO trackingDTO) throws URISyntaxException {
        log.debug("REST request to save Tracking : {}", trackingDTO);
        if (trackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new tracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrackingDTO result = trackingService.save(trackingDTO);
        return ResponseEntity.created(new URI("/api/trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trackings} : Updates an existing tracking.
     *
     * @param trackingDTO the trackingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trackingDTO,
     * or with status {@code 400 (Bad Request)} if the trackingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trackingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trackings")
    public ResponseEntity<TrackingDTO> updateTracking(@Valid @RequestBody TrackingDTO trackingDTO) throws URISyntaxException {
        log.debug("REST request to update Tracking : {}", trackingDTO);
        if (trackingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrackingDTO result = trackingService.save(trackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trackings} : get all the trackings.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackings in body.
     */
    @GetMapping("/trackings")
    public ResponseEntity<List<TrackingDTO>> getAllTrackings(Pageable pageable) {
        log.debug("REST request to get a page of Trackings");
        Page<TrackingDTO> page = trackingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trackings/:id} : get the "id" tracking.
     *
     * @param id the id of the trackingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trackingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trackings/{id}")
    public ResponseEntity<TrackingDTO> getTracking(@PathVariable Long id) {
        log.debug("REST request to get Tracking : {}", id);
        Optional<TrackingDTO> trackingDTO = trackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackingDTO);
    }

    /**
     * {@code DELETE  /trackings/:id} : delete the "id" tracking.
     *
     * @param id the id of the trackingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trackings/{id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable Long id) {
        log.debug("REST request to delete Tracking : {}", id);
        trackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
