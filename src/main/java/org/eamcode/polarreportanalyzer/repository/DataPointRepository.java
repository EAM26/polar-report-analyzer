package org.eamcode.polarreportanalyzer.repository;

import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointRepository extends JpaRepository<DataPoint, Long> {

}
