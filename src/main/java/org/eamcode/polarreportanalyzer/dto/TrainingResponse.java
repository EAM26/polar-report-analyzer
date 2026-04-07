package org.eamcode.polarreportanalyzer.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TrainingResponse(
        Long id,
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
        String cadenceAvg,
        String ascent,
        String descent,
        List<PhaseResponse> phases
) {
}
