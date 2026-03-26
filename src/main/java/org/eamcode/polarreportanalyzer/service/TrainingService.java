package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.util.CsvReader;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ModelMapper modelMapper;
    private final MetaDataService metaDataService;
    private final DataPointService dataPointService;
    private final CsvReader csvReader;

    public TrainingService(TrainingRepository trainingRepository, ModelMapper modelMapper, MetaDataService metaDataService, DataPointService dataPointService, CsvReader csvReader) {
        this.trainingRepository = trainingRepository;
        this.modelMapper = modelMapper;
        this.metaDataService = metaDataService;
        this.dataPointService = dataPointService;
        this.csvReader = csvReader;
    }

    @Transactional
    public TrainingResponse createTraining(TrainingRequest request) {
        Training training = modelMapper.mapToTrainingEntity(request);
        training.setCreatedAt(LocalDateTime.now());

//        Get all data from csv report for training
        List<String[]> allDataRows = getAllDataRows(training);
        metaDataService.setTrainingFields(training, allDataRows.get(1));
        dataPointService.addDataPointsForTraining(training, allDataRows);
        trainingRepository.save(training);

        return modelMapper.mapTrainingToResponse(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponse> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(modelMapper::mapTrainingToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingResponse getTrainingById(Long id) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        return modelMapper.mapTrainingToResponse(training);
    }

    @Transactional
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    @Transactional
    public TrainingResponse updateTraining(Long id, TrainingRequest request) {
        Training training = trainingRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + id));
        Training trainingUpdated = modelMapper.updateTrainingFromRequest(request, training);
        return modelMapper.mapTrainingToResponse(trainingRepository.save(trainingUpdated));
    }

    private List<String[]> getAllDataRows(Training training) {
        return csvReader.readDataRows(training.getPathToReport());
    }
}
