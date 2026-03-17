package org.eamcode.polarreportanalyzer.service;

import com.opencsv.exceptions.CsvValidationException;
import org.eamcode.polarreportanalyzer.util.CsvReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MetaDataReader {

    private final CsvReader csvReader;

    public MetaDataReader(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    public List<String[]> readMetaData(String filePath) throws CsvValidationException, IOException {
        return csvReader.readMetaData(filePath);
//        return new MetaData(metaRows.get(0), metaRows.get(1));
    }
}
