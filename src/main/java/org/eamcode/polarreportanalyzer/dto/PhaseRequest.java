package org.eamcode.polarreportanalyzer.dto;


public record PhaseRequest(
        int start,
        int stop,
        long trainingId
) {
}
