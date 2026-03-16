package org.eamcode.polarreportanalyzer.dto;

import java.time.LocalDateTime;

public record TrainingResponse(
        Long id,
        String name,
        LocalDateTime dateTime
) {
}
