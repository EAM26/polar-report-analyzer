package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseSnapshotResponse;
import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.PhaseSnapshotType;
import org.eamcode.polarreportanalyzer.repository.DataPointRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhaseSnapshotService {

    private final DataPointRepository dataPointRepository;

    public PhaseSnapshotService(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public List<PhaseSnapshotResponse> getSnapshots(Phase phase, List<DataPoint> dataPoints) {
        List<PhaseSnapshotResponse> snapshots = new ArrayList<>();
        for (PhaseSnapshotType phaseSnapshotType: PhaseSnapshotType.values()) {
            System.out.println("phaseSnapshot: " + phaseSnapshotType);
            snapshots.add(createSnapshot(phase, phaseSnapshotType));
        }

        return snapshots;
    }

    private PhaseSnapshotResponse createSnapshot(Phase phase, PhaseSnapshotType type) {
        int relativeSecond;
        if(type == PhaseSnapshotType.MIDPOINT) {
            relativeSecond = phase.getStart() + (phase.getStop() - phase.getStart()) /2;
        } else if (type.getPosition().equals("stop")) {
            relativeSecond = phase.getStop() + type.getOffsetSeconds();
        } else {
            relativeSecond = phase.getStart() + type.getOffsetSeconds();
        }

//        if (relativeSecond < phase.getStart() || relativeSecond > phase.getStop()) {
//            return null;
//        }
        DataPoint dataPoint = dataPointRepository.findDataPointByTrainingIdAndRelativeSecond(phase.getTraining().getId(), relativeSecond);

        return new PhaseSnapshotResponse(
                type,
                dataPoint.getHeartRate(),
                dataPoint.getDistance(),
                dataPoint.getCadence(),
                dataPoint.getPower()
        );
    }


}
