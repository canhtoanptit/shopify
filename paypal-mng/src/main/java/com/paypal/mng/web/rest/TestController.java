package com.paypal.mng.web.rest;

import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.worker.ShopifyWorker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final ShopifyWorker shopifyWorker;

    public TestController(ShopifyWorker shopifyWorker) {
        this.shopifyWorker = shopifyWorker;
    }

    @PostMapping("/tracking/retry")
    public void retryUploadTracking() {
        shopifyWorker.processRetry();
    }

    @GetMapping("/order")
    public OrderList getOrder() {
        return null;
//        return shopifyService.getOrdersBy("https://actimazo.myshopify.com/admin/api/2019-07/orders.json",
//            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
    }

//    @GetMapping("/transaction")
//    public TransactionList getTransaction() {
//        return shopifyService.getTransactions("https://actimazo.myshopify.com/admin/api/2019-07/orders/1339736129630/transactions.json",
//            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
//    }

//    @PostMapping("/tracking")
//    public void uploadTrackingInfo(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
//        List<TrackingReport> rs = fileService.uploadTracking(file);
//        if (rs != null) {
//            List<TrackingReport> reports = rs.stream().peek(trackingDTO -> {
//                Optional<Tracking> trackingOptional = trackingService.findByOrderNameAndTrackingNumber(trackingDTO.getOrderName(),
//                    trackingDTO.getTrackingNumber());
//                trackingOptional.ifPresent(tracking -> {
//                    Optional<PaypalHistoryDTO> data = paypalHistoryService.findByOrderIdAndTrackingNumber(tracking.getOrder().getShopifyOrderId(),
//                        trackingDTO.getTrackingNumber());
//                    data.ifPresent(paypalHistoryDTO -> {
//                        trackingDTO.setStatus(Constants.ORDER_PROCESSED);
//                        if (paypalHistoryDTO.getStatus() == Constants.PAYPAL_ADD_TRACKING_SUCCESS) {
//                            trackingDTO.setUploadPaypalStatus("SUCCESS");
//                        } else {
//                            trackingDTO.setUploadPaypalStatus("FAILED");
//                        }
//                    });
//                });
//            }).collect(Collectors.toList());
//            String fileName = "Result_" + Instant.now().toString() + "_" + file.getOriginalFilename() + "csv";
//            response.setContentType("text/csv");
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=" + fileName + "");
//
//            //create a csv writer
//            StatefulBeanToCsv<TrackingReport> writer = new StatefulBeanToCsvBuilder<TrackingReport>(response.getWriter())
//                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withOrderedResults(false)
//                .build();
//
//            //write all users to csv file
//            writer.write(reports);
//        }
//    }
}
