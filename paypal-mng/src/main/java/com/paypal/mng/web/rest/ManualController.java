package com.paypal.mng.web.rest;

import com.paypal.mng.service.FileService;
import com.paypal.mng.service.StoreService;
import com.paypal.mng.service.dto.csv.TrackingManual;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.util.RestUtil;
import com.paypal.mng.worker.ShopifyWorker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ManualController {

    private final FileService fileService;

    private final StoreService storeService;

    private final RestTemplate restTemplate;

    private final ShopifyWorker shopifyWorker;

    public ManualController(FileService fileService, StoreService storeService, RestTemplate restTemplate, ShopifyWorker shopifyWorker) {
        this.fileService = fileService;
        this.storeService = storeService;
        this.restTemplate = restTemplate;
        this.shopifyWorker = shopifyWorker;
    }

    @PostMapping("/manual/order")
    public void uploadManual(@RequestParam("file") MultipartFile file) throws IOException {
        List<TrackingManual> dataUpload = fileService.uploadManual(file);
        if (dataUpload != null && !dataUpload.isEmpty()) {
            dataUpload.forEach(trackingManual -> {
                storeService.findByStoreName(trackingManual.getStoreName())
                    .ifPresent(storeDTO -> {
                        ResponseEntity<ShopifyOrder> rs = restTemplate.exchange(storeDTO.getShopifyApiUrl() + trackingManual.getOrderId() + ".json",
                            HttpMethod.GET, new HttpEntity<ShopifyOrder>(RestUtil.createHeaders(storeDTO.getShopifyApiKey(),
                                storeDTO.getShopifyApiPassword())), ShopifyOrder.class);
                        if (rs.getStatusCode() == HttpStatus.OK) {
                            this.shopifyWorker.processShopifyOrder(storeDTO, rs.getBody());
                        }
                    });
            });
        }
    }
}
