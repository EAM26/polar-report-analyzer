package org.eamcode.polarreportanalyzer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer relativeSecond;
    private LocalTime timeStamp;
    private Integer heartRate;
    private Double speed;
    private Integer cadence;
    private Double altitude;
    private Double strideLength;
    private Double distance;
    private Double temperature;
    private Integer power;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
