package com.paypal.mng.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.paypal.mng.service.FileService;
import com.paypal.mng.service.dto.csv.TrackingReport;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<TrackingReport> uploadTracking(MultipartFile multipartFile) throws IOException {
        try (Reader reader = new InputStreamReader(multipartFile.getInputStream())) {
            CsvToBean<TrackingReport> csvToBean = new CsvToBeanBuilder<TrackingReport>(reader).withType(TrackingReport.class)
                .withIgnoreLeadingWhiteSpace(true).build();
            return csvToBean.parse();
        }
    }
}
