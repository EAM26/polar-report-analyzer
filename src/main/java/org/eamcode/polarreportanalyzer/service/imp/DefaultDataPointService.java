package org.eamcode.polarreportanalyzer.service.imp;

import org.eamcode.polarreportanalyzer.exception.CsvParsingException;
import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.DataPointRepository;
import org.eamcode.polarreportanalyzer.service.DataPointService;
import org.eamcode.polarreportanalyzer.util.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DefaultDataPointService implements DataPointService {


    private final DateTimeFormatter dateTimeFormatter;
    private final DataPointRepository dataPointRepository;

    public DefaultDataPointService(DateTimeFormatter dateTimeFormatter, DataPointRepository dataPointRepository) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.dataPointRepository = dataPointRepository;
    }

    public void addDataPointsForTraining(Training training, List<String[]> dataRows) {
        for (int i = 3; i < dataRows.size(); i++) {
            DataPoint dataPoint = new DataPoint();
            String[] row = dataRows.get(i);

            try {
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
            } catch (NumberFormatException e) {
                throw new CsvParsingException("Invalid value in row: " + i, e);
            }

            dataPoint.setRelativeSecond(i - 3);

            training.addToDataPoint(dataPoint);


        }
    }

    public List<DataPoint> getPhaseDataPoints(Phase phase) {
        return dataPointRepository.findByTrainingIdAndRelativeSecondBetweenOrderByRelativeSecondAsc(
                phase.getTraining().getId(), phase.getStart(), phase.getStop());
    }
}
