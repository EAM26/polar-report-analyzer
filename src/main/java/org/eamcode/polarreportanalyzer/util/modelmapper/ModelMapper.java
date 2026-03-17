package org.eamcode.polarreportanalyzer.util.modelmapper;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.Training;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public TrainingResponse mapTrainingToResponse(Training training) {
        return new TrainingResponse(
                training.getId(),
                training.getName(),
                training.getDateTime(),
                training.getSport(),
                training.getDescription(),
                training.getPathToReport(),
                training.getRpe(),
                training.getCreatedAt()
        );
    }

    public Training mapToTrainingEntity(TrainingRequest request) {
        return Training.builder()
                .name(request.name())
                .dateTime(request.dateTime())
                .sport(request.sport())
                .description(request.description())
                .pathToReport(request.pathToReport())
                .rpe(request.rpe())
                .createdAt(request.createdAt())
                .build();

    }

    public Training updateTrainingFromRequest(TrainingRequest request, Training training) {
        training.setName(request.name());
        training.setDateTime(request.dateTime());
        training.setSport(request.sport());
        training.setDescription(request.description());
        training.setPathToReport(request.pathToReport());
        training.setRpe(request.rpe());
        training.setCreatedAt(request.createdAt());

        return training;
    }

    public Phase mapToPhaseEntity(PhaseRequest request) {
        return Phase.builder()
                .start(request.start())
                .stop(request.stop())
                .hrAvg(request.hrAvg())
                .speedAvg(request.speedAvg())
                .totalDistance(request.totalDistance())
                .build();
    }

    public PhaseResponse mapPhaseToResponse(Phase phase) {
        return new PhaseResponse(
                phase.getId(),
                phase.getStart(),
                phase.getStop(),
                phase.getHrAvg(),
                phase.getSpeedAvg(),
                phase.getTotalDistance()
        );
    }
}
