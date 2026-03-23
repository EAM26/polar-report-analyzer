package org.eamcode.polarreportanalyzer.dto;

public record PhaseResponse(
        long id,
        String name,
        int start,
        int stop,
        Double hrAvg,
        Double speedAvg,
        Double distance,
        long trainingId
) {
}
