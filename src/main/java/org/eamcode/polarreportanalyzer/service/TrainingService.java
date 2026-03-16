package org.eamcode.polarreportanalyzer.service;

import org.antlr.v4.runtime.misc.LogManager;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }
}
