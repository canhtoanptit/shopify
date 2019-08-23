package com.paypal.mng.web.rest;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.paypal.mng.config.Constants;
import com.paypal.mng.domain.Tracking;
import com.paypal.mng.service.FileService;
import com.paypal.mng.service.PaypalHistoryService;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.PaypalHistoryDTO;
import com.paypal.mng.service.dto.csv.TrackingReport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    private final PaypalHistoryService paypalHistoryService;

    private final TrackingService trackingService;

    public FileController(FileService fileService, PaypalHistoryService paypalHistoryService, TrackingService trackingService) {
        this.fileService = fileService;
        this.paypalHistoryService = paypalHistoryService;
        this.trackingService = trackingService;
    }

    @PostMapping("/tracking")
    public void uploadTrackingInfo(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        List<TrackingReport> rs = fileService.uploadTracking(file);
        if (rs != null) {
            List<TrackingReport> reports = rs.stream().peek(trackingDTO -> {
                Optional<Tracking> trackingOptional = trackingService.findByOrderNameAndTrackingNumber(trackingDTO.getOrderName(),
                    trackingDTO.getTrackingNumber());
                trackingOptional.ifPresent(tracking -> {
                    Optional<PaypalHistoryDTO> data = paypalHistoryService.findByOrderIdAndTrackingNumber(tracking.getOrder().getShopifyOrderId(),
                        trackingDTO.getTrackingNumber());
                    data.ifPresent(paypalHistoryDTO -> {
                        trackingDTO.setStatus(Constants.ORDER_PROCESSED);
                        if (paypalHistoryDTO.getStatus() == Constants.PAYPAL_ADD_TRACKING_SUCCESS) {
                            trackingDTO.setUploadPaypalStatus("SUCCESS");
                        } else {
                            trackingDTO.setUploadPaypalStatus("FAILED");
                        }
                    });
                });
            }).collect(Collectors.toList());
            response.setContentType("text/csv");

            //create a csv writer
            StatefulBeanToCsv<TrackingReport> writer = new StatefulBeanToCsvBuilder<TrackingReport>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

            //write all users to csv file
            writer.write(reports);
        }
    }
}
