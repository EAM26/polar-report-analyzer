package org.eamcode.polarreportanalyzer.model;

import lombok.Getter;

@Getter
public enum PhaseSnapshotType {
    START( "start",0),

    PLUS_10S("start",10),
    PLUS_30S("start",30),
    PLUS_60S("start",60),

    MIDPOINT(null, 0), // speciale case

    MINUS_10S("stop",-10),
    MINUS_30S("stop",-30),
    MINUS_60S("stop",-60),

    STOP("stop",0);

    private final String position;
    private final int offsetSeconds;

    PhaseSnapshotType(String position ,int offsetSeconds) {
        this.position = position;
        this.offsetSeconds = offsetSeconds;
    }


}
