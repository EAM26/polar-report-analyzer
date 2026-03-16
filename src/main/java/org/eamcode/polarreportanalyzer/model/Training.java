package org.eamcode.polarreportanalyzer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private LocalDateTime dateTime;
    private String sport;
    private String pathToReport;

    public Training(String name, LocalDateTime dateTime, String sport, String pathToReport) {
        this.name = name;
        this.dateTime = dateTime;
        this.sport = sport;
        this.pathToReport = pathToReport;
    }
}
