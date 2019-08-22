package com.paypal.mng.service;

import com.paypal.mng.service.dto.csv.TrackingReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<TrackingReport> uploadTracking(MultipartFile multipartFile) throws IOException;
}
