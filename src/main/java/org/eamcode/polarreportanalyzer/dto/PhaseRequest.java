package org.eamcode.polarreportanalyzer.dto;


public record PhaseRequest(
        int start,
        int stop,
        double hrAvg,
        double speedAvg,
        double totalDistance
) {
}
