package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.util.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        Training createdTraining = trainingRepository.save(modelMapper.mapToTrainingEntity(request));
        return modelMapper.mapTrainingToResponse(createdTraining);
    }

    public List<TrainingResponse> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(modelMapper::mapTrainingToResponse)
                .toList();
    }

    public TrainingResponse getTrainingById(Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        return modelMapper.mapTrainingToResponse(training);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    public TrainingResponse updateTraining(Long id, TrainingRequest request) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        Training trainingUpdated = modelMapper.updateTrainingFromRequest(request, training);
        return modelMapper.mapTrainingToResponse(trainingRepository.save(trainingUpdated));
    }
}
