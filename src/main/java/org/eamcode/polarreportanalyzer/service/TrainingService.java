package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
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
        Training createdTraining = trainingRepository.save(modelMapper.mapToEntity(request));
        return modelMapper.mapToResponse(createdTraining);
    }

    public List<TrainingResponse> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(modelMapper::mapToResponse)
                .toList();
    }

    public TrainingResponse getTrainingById(Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        return modelMapper.mapToResponse(training);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    public TrainingResponse updateTraining(Long id, TrainingRequest request) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        Training trainingUpdated = modelMapper.updateEntityFromRequest(request, training);
        return modelMapper.mapToResponse(trainingRepository.save(trainingUpdated));
    }
}
