package org.eamcode.polarreportanalyzer.controller;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.service.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<TrainingResponse> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public TrainingResponse getTrainingById(@PathVariable Long id) {
        return trainingService.getTrainingById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTrainingById(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return "Deleted training with id " + id;
    }

    @PutMapping("/{id}")
    public TrainingResponse updateTraining(@PathVariable Long id, @RequestBody TrainingRequest request) {
        return trainingService.updateTraining(id, request);

    }
}
