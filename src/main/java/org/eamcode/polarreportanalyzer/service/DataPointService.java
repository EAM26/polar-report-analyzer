package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Training;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DataPointService {


    public void addDataPointsForTraining(Training training, List<String[]> dataRows) {
        for (int i = 3; i < dataRows.size(); i++) {
            DataPoint dataPoint = new DataPoint();
            String[] row = dataRows.get(i);
            dataPoint.setRelativeSecond(i-3);
            dataPoint.setTimeStamp(row[1].isEmpty() ? null : LocalTime.parse(row[1]));
            dataPoint.setHeartRate(row[2].isEmpty() ? null : Integer.parseInt(row[2]));
          

            training.addToDataPoint(dataPoint);
        }
    }
}
