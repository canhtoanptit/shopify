package com.paypal.mng.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.paypal.mng.service.FileService;
import com.paypal.mng.service.dto.TrackingDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<TrackingDTO> uploadTracking(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        try (Reader reader = new FileReader(file)) {
            CsvToBean<TrackingDTO> csvToBean = new CsvToBeanBuilder<TrackingDTO>(reader).withType(TrackingDTO.class)
                .withIgnoreLeadingWhiteSpace(true).build();
            return csvToBean.parse();
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
