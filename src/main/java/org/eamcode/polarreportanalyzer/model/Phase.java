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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int start;
    private int stop;
    private double hrAvg;
    private double speedAvg;
    private double totalDistance;

//    @ManyToOne
//    @JoinColumn(name = "training_id")
//    private Training training;
}
