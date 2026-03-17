package org.eamcode.polarreportanalyzer.dto;

public record PhaseResponse(
        long id,
        int start,
        int stop,
        double hrAvg,
        double speedAvg,
        double totalDistance
) {
}
