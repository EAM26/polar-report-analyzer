package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.model.Training;
import org.springframework.stereotype.Component;

@Component
public class MetaDataService {

    public void setTrainingFields(Training training, String[] metaData) {
        training.setSport(metaData[1]);
        training.setDate(metaData[2]);
        training.setStartTime(metaData[3]);
        training.setName(training.getSport() + ": " +
                training.getDate() + " " + training.getStartTime());
        training.setDuration(metaData[4]);
        training.setTotalDistance(metaData[5]);
        training.setHrAvg(metaData[6]);
        training.setSpeedAvg(metaData[7]);
        training.setCadenceAvg(metaData[15]);
        training.setAscent(metaData[19]);
        training.setDescent(metaData[20]);
    }
}
