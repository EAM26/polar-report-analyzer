package org.eamcode.polarreportanalyzer.repository;

import org.eamcode.polarreportanalyzer.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

    Optional<Phase> findTopByTrainingIdOrderByStopDesc(Long trainingId);
}
