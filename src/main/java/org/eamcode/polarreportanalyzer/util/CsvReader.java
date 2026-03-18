package org.eamcode.polarreportanalyzer.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvReader {

//    public List<String[]> readMetaData(String resourcePath) throws IOException, CsvValidationException {
//        List<String[]> firstTwoRows = new ArrayList<>();
//
//        ClassPathResource resource = new ClassPathResource(resourcePath);
//
//        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
//             CSVReader csvReader = new CSVReader(reader)) {
//
//            String[] header = csvReader.readNext();
//            if (header != null) {
//                firstTwoRows.add(header);
//            }
//
//            String[] values = csvReader.readNext();
//            if (values != null) {
//                firstTwoRows.add(values);
//            }
//        }
//        return firstTwoRows;
//    }

    public List<String[]> readDataRows(String resourcePath)  {

        ClassPathResource resource = new ClassPathResource(resourcePath);

        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader).build())
        {
            return csvReader.readAll();
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}