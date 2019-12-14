package com.paypal.mng.service.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.paypal.mng.service.FileService;
import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.dto.csv.TrackingManual;
import com.paypal.mng.service.dto.csv.TrackingReport;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public List<TrackingManual> uploadManual(MultipartFile multipartFile) throws IOException {
        try (Reader reader = new InputStreamReader(multipartFile.getInputStream())) {
            CsvToBean<TrackingManual> csvToBean = new CsvToBeanBuilder<TrackingManual>(reader).withType(TrackingManual.class)
                .withIgnoreLeadingWhiteSpace(true).build();
            return csvToBean.parse();
        }
    }

    @Override
    public String writeOrderDaily(List<OrderDailyDTO> orderDailyDTOS) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Date date = new Date();
        String path = "/home/ec2-user/toannc/files/" + new SimpleDateFormat("yyyy-MM-dd").format(date) + ".csv";
        Writer writer  = new FileWriter( path);
        writer.append("ID,NAME,EMAIL,FINANCIAL_STATUS,PAID_AT,LINE_ITEM_QUANTITY,LINE_ITEM_NAME,SHIPPING_NAME,SHIPPING_ADDRESS1," +
            "SHIPPING_STREET,SHIPPING_ADDRESS2,SHIPPING_COMPANY,SHIPPING_CITY,SHIPPING_ZIP,SHIPPING_PROVINCE,SHIPPING_COUNTRY,SHIPPING_PHONE,NOTE \n");

        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .build();
        sbc.write(orderDailyDTOS);
        writer.close();
        return path;
    }
}
