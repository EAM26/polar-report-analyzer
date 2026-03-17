package org.eamcode.polarreportanalyzer.controller;

import com.opencsv.exceptions.CsvValidationException;
import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {

        this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<TrainingResponse> createTraining(@RequestBody TrainingRequest request) throws CsvValidationException, IOException {
        TrainingResponse createdTraining = trainingService.createTraining(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTraining.id())
                .toUri();

        return ResponseEntity.created(location).body(createdTraining);
    }

    @GetMapping
    public ResponseEntity<List<TrainingResponse>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingResponse> getTrainingById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.getTrainingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingById(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingResponse> updateTraining(@PathVariable Long id, @RequestBody TrainingRequest request) {
        return ResponseEntity.ok(trainingService.updateTraining(id, request));

    }
}
