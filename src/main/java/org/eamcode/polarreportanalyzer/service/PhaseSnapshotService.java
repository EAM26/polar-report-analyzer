package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseSnapshotResponse;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.model.PhaseSnapshotType;
import org.eamcode.polarreportanalyzer.repository.DataPointRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhaseSnapshotService {

    private final DataPointRepository dataPointRepository;

    public PhaseSnapshotService(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public List<PhaseSnapshotResponse> getSnapshots(Phase phase) {
        List<PhaseSnapshotResponse> snapshots = new ArrayList<>();
        for (PhaseSnapshotType phaseSnapshotType : PhaseSnapshotType.values()) {
            Optional<PhaseSnapshotResponse> snapshotResponse = createSnapshot(phase, phaseSnapshotType);
            snapshotResponse.ifPresent(snapshots::add);
        }

        return snapshots;
    }

    private Optional<PhaseSnapshotResponse> createSnapshot(Phase phase, PhaseSnapshotType type) {
        int relativeSecond;
        if (type == PhaseSnapshotType.MIDPOINT) {
            relativeSecond = phase.getStart() + (phase.getStop() - phase.getStart()) / 2;
        } else if (type.getPosition().equals("stop")) {
            relativeSecond = phase.getStop() + type.getOffsetSeconds();
        } else {
            relativeSecond = phase.getStart() + type.getOffsetSeconds();
        }

        return dataPointRepository.findDataPointByTrainingIdAndRelativeSecond(
                        phase.getTraining().getId(),
                        relativeSecond
                )
                .map(dataPoint -> (
                        new PhaseSnapshotResponse(
                                type,
                                dataPoint.getHeartRate(),
                                dataPoint.getDistance(),
                                dataPoint.getCadence(),
                                dataPoint.getPower()
                        )
                ));


    }


}
