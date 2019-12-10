package com.paypal.mng.worker;

import com.paypal.mng.service.FileService;
import com.paypal.mng.service.OrderDailyService;
import com.paypal.mng.service.dto.OrderDailyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDailyWorker {

    private final Logger logger = LoggerFactory.getLogger(OrderDailyWorker.class);

    private final OrderDailyService orderDailyService;

    private final FileService fileService;

    public OrderDailyWorker(OrderDailyService orderDailyService, FileService fileService) {
        this.orderDailyService = orderDailyService;
        this.fileService = fileService;
    }

    @Scheduled(cron = "0 50 23 * * ?")
    void getOrderDaily() {
        try {
            List<OrderDailyDTO> orders = orderDailyService.findAll();
            String filePath = fileService.writeOrderDaily(orders);
            logger.info("Write file to {}", filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
