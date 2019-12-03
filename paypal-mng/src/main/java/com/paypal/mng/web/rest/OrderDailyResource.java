package com.paypal.mng.web.rest;

import com.paypal.mng.service.OrderDailyService;
import com.paypal.mng.service.dto.OrderDailyDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing
 */
@RestController
@RequestMapping("/api")
public class OrderDailyResource {

    private final Logger log = LoggerFactory.getLogger(OrderDailyResource.class);

    private static final String ENTITY_NAME = "orderDaily";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDailyService orderDailyService;

    public OrderDailyResource(OrderDailyService orderDailyService) {
        this.orderDailyService = orderDailyService;
    }

    /**
     * {@code GET  /order-dailies} : get all the orderDailies.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDailies in body.
     */
    @GetMapping("/order-dailies")
    public ResponseEntity<List<OrderDailyDTO>> getAllOrderDailies(Pageable pageable) {
        log.debug("REST request to get a page of OrderDailies");
        List<OrderDailyDTO> data = orderDailyService.findAll();
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok().headers(headers).body(data);
    }

    /**
     * {@code GET  /order-dailies/:id} : get the "id" orderDaily.
     *
     * @param id the id of the orderDaily to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDaily, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-dailies/{id}")
    public ResponseEntity<OrderDailyDTO> getOrderDaily(@PathVariable Long id) {
        log.debug("REST request to get OrderDaily : {}", id);
        Optional<OrderDailyDTO> orderDaily = orderDailyService.findById(id);
        return ResponseUtil.wrapOrNotFound(orderDaily);
    }
}
