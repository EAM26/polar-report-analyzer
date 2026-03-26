package org.eamcode.polarreportanalyzer.dto;

import java.util.List;

public record PhaseResponse(
        Long id,
        String name,
        int duration,
        int start,
        int stop,
        Integer hrMax,
        Integer hrMin,
        Double hrAvg,
        Double speedAvg,
        Double distance,
        Long trainingId,
        List<PhaseSnapshotResponse> snapshots
) {
}
