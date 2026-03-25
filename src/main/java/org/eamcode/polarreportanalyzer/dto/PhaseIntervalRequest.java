package org.eamcode.polarreportanalyzer.dto;

import java.util.List;

public record PhaseIntervalRequest(
        Integer factor,
        List<PhaseRequest> requests
) {
}
