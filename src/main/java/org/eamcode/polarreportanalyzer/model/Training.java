package org.eamcode.polarreportanalyzer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String pathToReport;
    private Integer rpe;
    private LocalDateTime createdAt;

    //    meta data
    private String date;
    private String startTime;
    private String sport;
    private String duration;
    private String hrAvg;
    private String speedAvg;
    private String totalDistance;
    private String cadenceAvg;
    private String ascent;
    private String descent;


    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Phase> phases = new ArrayList<>();

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DataPoint> dataPoints = new ArrayList<>();

    public void addToDataPoint(DataPoint dataPoint) {
        dataPoint.setTraining(this);
        dataPoints.add(dataPoint);
    }
}
