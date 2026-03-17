package org.eamcode.polarreportanalyzer.dto;

import java.time.LocalDateTime;

public record TrainingRequest(
//        String name,
//        String dateTime,
//        String sport,
//        String description,
//        String pathToReport,
//        Integer rpe,
//        LocalDateTime createdAt
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
        String totalDistance
) {
}
