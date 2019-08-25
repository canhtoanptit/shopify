package com.paypal.mng.web.rest;

import com.paypal.mng.domain.Order;
import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.PaypalHistoryService;
import com.paypal.mng.service.dto.PaypalHistoryDTO;
import com.paypal.mng.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.paypal.mng.domain.PaypalHistory}.
 */
@RestController
@RequestMapping("/api")
public class PaypalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PaypalHistoryResource.class);

    private static final String ENTITY_NAME = "paypalHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaypalHistoryService paypalHistoryService;

    private final OrderService orderService;

    public PaypalHistoryResource(PaypalHistoryService paypalHistoryService, OrderService orderService) {
        this.paypalHistoryService = paypalHistoryService;
        this.orderService = orderService;
    }

    /**
     * {@code POST  /paypal-histories} : Create a new paypalHistory.
     *
     * @param paypalHistoryDTO the paypalHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paypalHistoryDTO, or with status {@code 400 (Bad Request)} if the paypalHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paypal-histories")
    public ResponseEntity<PaypalHistoryDTO> createPaypalHistory(@Valid @RequestBody PaypalHistoryDTO paypalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save PaypalHistory : {}", paypalHistoryDTO);
        if (paypalHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new paypalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaypalHistoryDTO result = paypalHistoryService.save(paypalHistoryDTO);
        return ResponseEntity.created(new URI("/api/paypal-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paypal-histories} : Updates an existing paypalHistory.
     *
     * @param paypalHistoryDTO the paypalHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paypalHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the paypalHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paypalHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paypal-histories")
    public ResponseEntity<PaypalHistoryDTO> updatePaypalHistory(@Valid @RequestBody PaypalHistoryDTO paypalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update PaypalHistory : {}", paypalHistoryDTO);
        if (paypalHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaypalHistoryDTO result = paypalHistoryService.save(paypalHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paypalHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paypal-histories} : get all the paypalHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paypalHistories in body.
     */
    @GetMapping("/paypal-histories")
    public ResponseEntity<List<PaypalHistoryDTO>> getAllPaypalHistories(@RequestParam("searchParam") String searchParam, Pageable pageable) {
        log.debug("REST request to get a page of PaypalHistories");
        Page<PaypalHistoryDTO> page;
        if (searchParam != null && !searchParam.trim().isEmpty()) {
            Page<Order> optionalOrder = orderService.findByOrderNameContain(searchParam);
            if (!optionalOrder.isEmpty()) {
                page = paypalHistoryService.findAllByShopifyOrderIds(optionalOrder.get().map(Order::getShopifyOrderId).collect(Collectors.toList()), pageable);
            } else {
                page = paypalHistoryService.findAllByAuthorizationKey(searchParam, pageable);
            }
        } else {
            page = paypalHistoryService.findAll(pageable);
        }
        for (PaypalHistoryDTO paypalHistoryDTO : page) {
            orderService.findByShopifyOrderId(paypalHistoryDTO.getShopifyOrderId())
                .ifPresent(orderDTO -> {
                    paypalHistoryDTO.setShopifyOrderName(orderDTO.getOrderName());
                });
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paypal-histories/:id} : get the "id" paypalHistory.
     *
     * @param id the id of the paypalHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paypalHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paypal-histories/{id}")
    public ResponseEntity<PaypalHistoryDTO> getPaypalHistory(@PathVariable Long id) {
        log.debug("REST request to get PaypalHistory : {}", id);
        Optional<PaypalHistoryDTO> paypalHistoryDTO = paypalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paypalHistoryDTO);
    }

    /**
     * {@code DELETE  /paypal-histories/:id} : delete the "id" paypalHistory.
     *
     * @param id the id of the paypalHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paypal-histories/{id}")
    public ResponseEntity<Void> deletePaypalHistory(@PathVariable Long id) {
        log.debug("REST request to delete PaypalHistory : {}", id);
        paypalHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
