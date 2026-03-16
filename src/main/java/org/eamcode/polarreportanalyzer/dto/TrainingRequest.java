package org.eamcode.polarreportanalyzer.dto;

import java.time.LocalDateTime;

public record TrainingRequest(
        String name,
        LocalDateTime dateTime,
        String sport,
        String pathToReport
) {
}
