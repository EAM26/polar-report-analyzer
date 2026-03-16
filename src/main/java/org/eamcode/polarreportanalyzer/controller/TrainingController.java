package org.eamcode.polarreportanalyzer.controller;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.service.TrainingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {

        this.trainingService = trainingService;
    }

    @PostMapping
    public TrainingResponse createTraining(@RequestBody TrainingRequest request) {
        return trainingService.createTraining(request);
    }
}
