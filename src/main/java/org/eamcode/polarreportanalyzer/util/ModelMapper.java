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
                training.getPathToReport()
        );
    }

    public Training toEntity(TrainingRequest request) {
        return new Training(
                request.name(),
                request.dateTime(),
                request.sport(),
                request.pathToReport()
        );
    }
}
