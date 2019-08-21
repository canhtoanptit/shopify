package com.paypal.mng.service.impl;

import com.paypal.mng.service.FileService;
import com.paypal.mng.service.dto.TrackingDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<TrackingDTO> uploadTracking(MultipartFile multipartFile) {
        return null;
    }
}
