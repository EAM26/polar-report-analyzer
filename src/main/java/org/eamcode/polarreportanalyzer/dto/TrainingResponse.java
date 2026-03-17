package org.eamcode.polarreportanalyzer.dto;

import java.time.LocalDateTime;

public record TrainingResponse(
        Long id,
        String name,
        LocalDateTime dateTime,
        String sport,
        String description,
        String pathToReport,
        int rpe,
        LocalDateTime createdAt) {
}
