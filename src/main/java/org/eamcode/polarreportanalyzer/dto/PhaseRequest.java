package org.eamcode.polarreportanalyzer.dto;


public record PhaseRequest(
        int start,
        int stop,
        Double hrAvg,
        Double speedAvg,
        Double distance,
        long trainingId
) {
}
