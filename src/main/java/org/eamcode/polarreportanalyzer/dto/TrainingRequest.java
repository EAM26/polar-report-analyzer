package org.eamcode.polarreportanalyzer.dto;

import org.eamcode.polarreportanalyzer.model.Phase;

import java.time.LocalDateTime;
import java.util.List;

public record TrainingRequest(
        String name,
        String description,
        String pathToReport,
        Integer rpe,
        LocalDateTime createdAt,
        String date,
        String startTime,
        String sport,
        String duration,
        String hrAvg,
        String speedAvg,
        String totalDistance,
        List<Phase> phases
) {
}
