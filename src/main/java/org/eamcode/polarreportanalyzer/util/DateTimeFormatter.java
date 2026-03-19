package org.eamcode.polarreportanalyzer.util;

import org.springframework.stereotype.Component;

@Component
public class DateTimeFormatter {

    public int formatToSeconds(String durationString) {
        Integer minutesToSeconds = Integer.parseInt(durationString.split(":")[0]) * 60;
        Integer seconds = Integer.parseInt(durationString.split(":")[1]);

        return minutesToSeconds + seconds;

    }
}
