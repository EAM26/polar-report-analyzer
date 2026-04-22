package org.eamcode.polarreportanalyzer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int duration;
    private int start;
    private int stop;
    private Integer hrMax;
    private Integer hrMin;
    private Double hrAvg;
    private Double speedAvg;
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
