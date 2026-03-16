package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ModelMapper modelMapper;

    public TrainingService(TrainingRepository trainingRepository, ModelMapper modelMapper) {
        this.trainingRepository = trainingRepository;
        this.modelMapper = modelMapper;
    }

    public TrainingResponse createTraining(TrainingRequest request) {
        Training createdTraining = trainingRepository.save(modelMapper.toEntity(request));
        return modelMapper.mapToResponse(createdTraining);

    }

    public List<TrainingResponse> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(modelMapper::mapToResponse)
                .toList();
    }


    public TrainingResponse getTrainingById(Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No training found with id: " + id));
        return modelMapper.mapToResponse(training);

    }
}
