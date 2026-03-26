package org.eamcode.polarreportanalyzer.dto;


public record PhaseRequest(
        String name,
        int duration,
        Long trainingId
) {
}
