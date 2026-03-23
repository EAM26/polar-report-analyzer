package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.DataPointRepository;
import org.eamcode.polarreportanalyzer.util.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DataPointService {


    private final DateTimeFormatter dateTimeFormatter;
    private final DataPointRepository dataPointRepository;

    public DataPointService(DateTimeFormatter dateTimeFormatter, DataPointRepository dataPointRepository) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.dataPointRepository = dataPointRepository;
    }

    public void addDataPointsForTraining(Training training, List<String[]> dataRows) {
        for (int i = 3; i < dataRows.size(); i++) {
            DataPoint dataPoint = new DataPoint();
            String[] row = dataRows.get(i);
            dataPoint.setRelativeSecond(i-3);
            dataPoint.setTimeStamp(row[1].isEmpty() ? null : LocalTime.parse(row[1]));
            dataPoint.setHeartRate(row[2].isEmpty() ? null : Integer.parseInt(row[2]));
            dataPoint.setSpeed(row[3].isEmpty() ? null : Double.parseDouble(row[3]));

            Integer paceInSeconds = row[4].isEmpty() ? null : dateTimeFormatter.formatToSeconds(row[4]);
            dataPoint.setPaceSeconds(paceInSeconds);

            dataPoint.setCadence(row[5].isEmpty() ? null : Integer.parseInt(row[5]));
            dataPoint.setAltitude(row[6].isEmpty() ? null : Double.parseDouble(row[6]));
            dataPoint.setStrideLength(row[7].isEmpty() ? null : Double.parseDouble(row[7]));
            dataPoint.setDistance(row[8].isEmpty() ? null : Double.parseDouble(row[8]));
            dataPoint.setTemperature(row[9].isEmpty() ? null : Double.parseDouble(row[9]));
            dataPoint.setPower(row[10].isEmpty() ? null : Integer.parseInt(row[10]));

            training.addToDataPoint(dataPoint);
        }
    }

    public List<DataPoint> getPhaseDataPoints(PhaseRequest request) {
        return dataPointRepository.findByTrainingIdAndRelativeSecondBetweenOrderByRelativeSecondAsc(
                request.trainingId(), request.start(), request.stop());
    }
}
