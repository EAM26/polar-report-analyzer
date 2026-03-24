package org.eamcode.polarreportanalyzer.repository;

import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataPointRepository extends JpaRepository<DataPoint, Long> {
    List<DataPoint> findByTrainingIdAndRelativeSecondBetweenOrderByRelativeSecondAsc(
            Long trainingId,
            Integer start,
            Integer end
    );

    Optional<DataPoint> findDataPointByTrainingIdAndRelativeSecond(Long trainingId, Integer relativeSecond);
}
