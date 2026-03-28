package org.eamcode.polarreportanalyzer.dto;

import org.eamcode.polarreportanalyzer.model.PhaseSnapshotType;

public record PhaseSnapshotResponse(
        PhaseSnapshotType phaseSnapshotType,
        int hr,
        double distance,
        int cadence,
        int power,
        double temperature,
        double altitude

) {
}
