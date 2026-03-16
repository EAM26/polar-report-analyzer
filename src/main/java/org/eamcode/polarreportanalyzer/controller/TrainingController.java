package org.eamcode.polarreportanalyzer.controller;

import org.antlr.v4.runtime.misc.LogManager;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.service.TrainingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingRepository trainingRepository, TrainingService trainingService) {

        this.trainingService = trainingService;
    }

    @PostMapping
    public Long createTraining(@RequestBody Training training) {
        return trainingService.createTraining(training).getId();
    }
}
