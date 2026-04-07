package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.Training;

import java.util.List;

public interface DataPointService {
    void addDataPointsForTraining(Training training, List<String[]> dataRows);

    List<DataPoint> getPhaseDataPoints(Phase phase);
}
