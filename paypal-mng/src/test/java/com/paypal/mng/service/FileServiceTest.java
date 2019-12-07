package com.paypal.mng.service;

import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class FileServiceTest {
    FileService fileService = new FileServiceImpl();

//    @Test
    void testWriteCsvFile() {
        try {
            OrderDailyDTO orderDailyDTO = new OrderDailyDTO();
            orderDailyDTO.setLineItemQuantity(3);
            orderDailyDTO.setLineItemName("hello");
            orderDailyDTO.setShipingPhone("122-33-333");
            fileService.writeOrderDaily(Collections.singletonList(orderDailyDTO));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
