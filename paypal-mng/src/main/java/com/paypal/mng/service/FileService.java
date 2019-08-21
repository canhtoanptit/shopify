package com.paypal.mng.service;

import com.paypal.mng.service.dto.TrackingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<TrackingDTO> uploadTracking(MultipartFile multipartFile);
}
