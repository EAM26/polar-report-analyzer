package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseCalculatorService {

    private final DataPointService dataPointService;

    PhaseCalculatorService(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    public List<Double> calculateAvgs(Phase phase) {
        List<DataPoint> phaseDataPoints = dataPointService.getPhaseDataPoints(phase);

        return List.of(getHrAvg(phaseDataPoints), getTotalDistance(phaseDataPoints),
                getSpeedAvg(getTotalDistance(phaseDataPoints), phaseDataPoints));
    }

    public Double getHrAvg(List<DataPoint> dataPoints) {
        return dataPoints.stream()
                .mapToInt(DataPoint::getHeartRate)
                .average()
                .orElse(0d);
    }

    public Double getTotalDistance(List<DataPoint> dataPoints) {
        return dataPoints.getLast().getDistance() - dataPoints.getFirst().getDistance();
    }

    public Double getSpeedAvg(Double distance, List<DataPoint> dataPoints) {
        return distance / (dataPoints.getLast().getRelativeSecond() - dataPoints.getFirst().getRelativeSecond()) * 3.6;
    }
}
