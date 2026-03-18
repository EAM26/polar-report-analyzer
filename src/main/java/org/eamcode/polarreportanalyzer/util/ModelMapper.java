package org.eamcode.polarreportanalyzer.util;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.Training;
import org.eamcode.polarreportanalyzer.repository.TrainingRepository;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    private final TrainingRepository trainingRepository;

    public ModelMapper(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public TrainingResponse mapTrainingToResponse(Training training) {
        return new TrainingResponse(
                training.getId(),
                training.getName(),
                training.getDescription(),
                training.getPathToReport(),
                training.getRpe(),
                training.getCreatedAt(),
                training.getDate(),
                training.getStartTime(),
                training.getSport(),
                training.getDuration(),
                training.getHrAvg(),
                training.getSpeedAvg(),
                training.getTotalDistance(),
                training.getCadenceAvg(),
                training.getPhases().stream().map(this::mapPhaseToResponse).toList()
        );
    }

    public Training mapToTrainingEntity(TrainingRequest request) {

        return Training.builder()
                .name(request.name())
                .description(request.description())
                .pathToReport(request.pathToReport())
                .rpe(request.rpe())
                .createdAt(request.createdAt())
                .date(request.date())
                .startTime(request.startTime())
                .sport(request.sport())
                .duration(request.duration())
                .hrAvg(request.hrAvg())
                .speedAvg(request.speedAvg())
                .totalDistance(request.totalDistance())
                .cadenceAvg(request.cadenceAvg())
                .build();

    }

    public Training updateTrainingFromRequest(TrainingRequest request, Training training) {
        training.setName(request.name());
        training.setDescription(request.description());
        training.setPathToReport(request.pathToReport());
        training.setRpe(request.rpe());
        training.setCreatedAt(request.createdAt());
        training.setDate(request.date());
        training.setStartTime(request.startTime());
        training.setSport(request.sport());
        training.setDuration(request.duration());
        training.setHrAvg(request.hrAvg());
        training.setSpeedAvg(request.speedAvg());
        training.setTotalDistance(request.totalDistance());
        training.setCadenceAvg(training.getPathToReport());

        return training;
    }

    public Phase mapToPhaseEntity(PhaseRequest request) {
        Training training = trainingRepository.findById(request.trainingId()).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + request.trainingId()));
        return Phase.builder()
                .start(request.start())
                .stop(request.stop())
                .hrAvg(request.hrAvg())
                .speedAvg(request.speedAvg())
                .distance(request.distance())
                .training(training)
                .build();
    }

    public PhaseResponse mapPhaseToResponse(Phase phase) {
        return new PhaseResponse(
                phase.getId(),
                phase.getStart(),
                phase.getStop(),
                phase.getHrAvg(),
                phase.getSpeedAvg(),
                phase.getDistance(),
                phase.getTraining().getId()
        );
    }

    public Phase updatePhaseFromRequest(PhaseRequest request, Phase phase) {
        Training training = trainingRepository.findById(request.trainingId()).orElseThrow(() ->
                new RecordNotFoundException("No training found with id: " + request.trainingId()));
        phase.setStart(request.start());
        phase.setStop(request.stop());
        phase.setHrAvg(request.hrAvg());
        phase.setSpeedAvg(request.speedAvg());
        phase.setDistance(request.distance());
        phase.setTraining(training);

        return phase;
    }
}
