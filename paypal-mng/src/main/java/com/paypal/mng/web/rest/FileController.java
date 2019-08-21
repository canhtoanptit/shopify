package com.paypal.mng.web.rest;

import com.paypal.mng.service.FileService;
import com.paypal.mng.service.PaypalHistoryService;
import com.paypal.mng.service.dto.PaypalHistoryDTO;
import com.paypal.mng.service.dto.TrackingDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    private final PaypalHistoryService paypalHistoryService;

    public FileController(FileService fileService, PaypalHistoryService paypalHistoryService) {
        this.fileService = fileService;
        this.paypalHistoryService = paypalHistoryService;
    }

    @PostMapping("/tracking")
    public void uploadTrackingInfo(@RequestParam("file") MultipartFile file) throws IOException {
        List<TrackingDTO> rs = fileService.uploadTracking(file);
        List<PaypalHistoryDTO> paypalHistoryDTOList = new ArrayList<>();
        if (rs != null) {
            rs.forEach(trackingDTO -> {
                    Optional<PaypalHistoryDTO> data = paypalHistoryService.findByOrderIdAndTrackingNumber(trackingDTO.getOrderId(), trackingDTO.getTrackingNumber());
                data.ifPresent(paypalHistoryDTOList::add);
                }
            );
        }

    }
}
