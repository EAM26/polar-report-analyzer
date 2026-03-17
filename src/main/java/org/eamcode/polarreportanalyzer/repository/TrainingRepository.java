package org.eamcode.polarreportanalyzer.repository;

import lombok.NonNull;
import org.eamcode.polarreportanalyzer.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<@NonNull Training, @NonNull Long> {
}
