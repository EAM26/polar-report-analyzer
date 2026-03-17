package org.eamcode.polarreportanalyzer.util;

import org.eamcode.polarreportanalyzer.dto.TrainingRequest;
import org.eamcode.polarreportanalyzer.dto.TrainingResponse;
import org.eamcode.polarreportanalyzer.model.Training;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public TrainingResponse mapToResponse(Training training) {
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

    public Training mapToEntity(TrainingRequest request) {
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

    public Training updateEntityFromRequest(TrainingRequest request, Training training) {
        training.setName(request.name());
        training.setDateTime(request.dateTime());
        training.setSport(request.sport());
        training.setDescription(request.description());
        training.setPathToReport(request.pathToReport());
        training.setRpe(request.rpe());
        training.setCreatedAt(request.createdAt());

        return training;
    }
}
