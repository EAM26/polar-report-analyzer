package org.eamcode.polarreportanalyzer.service;

import com.opencsv.exceptions.CsvValidationException;
import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ModelMapper modelMapper;
    private final MetaDataReader metaDataReader;

    public TrainingService(TrainingRepository trainingRepository, ModelMapper modelMapper, MetaDataReader metaDataReader) {
        this.trainingRepository = trainingRepository;
        this.modelMapper = modelMapper;
        this.metaDataReader = metaDataReader;
    }

    public TrainingResponse createTraining(TrainingRequest request) throws CsvValidationException, IOException {
        Training training = modelMapper.mapToTrainingEntity(request);
        setTrainingFields(training);
        return modelMapper.mapTrainingToResponse(trainingRepository.save(training));
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

    private void setTrainingFields(Training training) throws CsvValidationException, IOException {
        String[] metaData = metaDataReader.readMetaData(training.getPathToReport()).getLast();
        training.setSport(metaData[1]);
        training.setDate(metaData[2]);
        training.setStartTime(metaData[3]);
        training.setName(training.getSport() + ": " +
                training.getDate() + " " + training.getStartTime());
        training.setDuration(metaData[4]);
        training.setTotalDistance(metaData[5]);
        training.setHrAvg(metaData[6]);
        training.setSpeedAvg(metaData[7]);

    }
}
