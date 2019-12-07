package com.paypal.mng.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.dto.csv.TrackingManual;
import com.paypal.mng.service.dto.csv.TrackingReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<TrackingReport> uploadTracking(MultipartFile multipartFile) throws IOException;

    List<TrackingManual> uploadManual(MultipartFile multipartFile) throws IOException;

    String writeOrderDaily(List<OrderDailyDTO> orderDailyDTOS) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
