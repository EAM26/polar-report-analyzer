package org.eamcode.polarreportanalyzer.dto;

import java.util.List;

public record PhaseResponse(
        long id,
        String name,
        int duration,
        int start,
        int stop,
        int hrMax,
        int hrMin,
        Double hrAvg,
        Double speedAvg,
        Double distance,
        long trainingId,
        List<PhaseSnapshotResponse> snapshots
) {
}
