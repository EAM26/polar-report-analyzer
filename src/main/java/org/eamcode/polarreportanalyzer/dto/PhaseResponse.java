package org.eamcode.polarreportanalyzer.dto;

public record PhaseResponse(
        long id,
        int start,
        int stop,
        Double hrAvg,
        Double speedAvg,
        Double distance,
        long trainingId
) {
}
