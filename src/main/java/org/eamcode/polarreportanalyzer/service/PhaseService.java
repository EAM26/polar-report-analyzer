package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.repository.PhaseRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PhaseService {
    private final ModelMapper modelMapper;
    private final PhaseRepository phaseRepository;
    private final PhaseCalculatorService phaseCalculatorService;

    public PhaseService(ModelMapper modelMapper, PhaseRepository phaseRepository, PhaseCalculatorService phaseCalculatorService) {
        this.modelMapper = modelMapper;
        this.phaseRepository = phaseRepository;
        this.phaseCalculatorService = phaseCalculatorService;
    }

    @Transactional
    public PhaseResponse createPhase(PhaseRequest request) {
        if (request.duration() < 1) {
            throw new IllegalArgumentException("Duration must be at least 1 second.");
        }
        Phase phase = modelMapper.mapToPhaseEntity(request);


//       set hard values
        setStartAndStop(phase);
        setMaxAndMinHr(phase);


//        set calculated values
        List<Double> values = phaseCalculatorService.calculateAvgs(phase);
        phase.setHrAvg(values.getFirst());
        phase.setDistance(values.get(1));
        phase.setSpeedAvg(values.getLast());

        return modelMapper.mapPhaseToResponse(phaseRepository.save(phase));
    }

    public List<PhaseResponse> getAllPhases() {
        return phaseRepository.findAll().stream().map(modelMapper::mapPhaseToResponse).toList();
    }

    public PhaseResponse getPhaseById(long id) {
        return phaseRepository.findById(id).map(modelMapper::mapPhaseToResponse).orElseThrow(() ->
                new RecordNotFoundException("No"));
    }


    public void deletePhaseById(long id) {
        phaseRepository.deleteById(id);
    }

    public PhaseResponse updatePhase(long id, PhaseRequest request) {
        Phase phase = phaseRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("No phase found with id: " + id));

        Phase phaseUpdated = phaseRepository.save(modelMapper.updatePhaseFromRequest(request, phase));
        return modelMapper.mapPhaseToResponse(phaseUpdated);
    }

    private void setStartAndStop(Phase phase) {
//        Todo check if stop is in bounds of relative seconds
        int lastSecondOfTraining = phase.getTraining().getDataPoints().getLast().getRelativeSecond();
        List<Phase> phases = phase.getTraining().getPhases();
        int startOfPhase;
        int stopOfPhase;
        if (phases.isEmpty()) {
            startOfPhase = 0;
            stopOfPhase = phase.getDuration() -1;
        } else {
            startOfPhase = phases.getLast().getStop() + 1;
            stopOfPhase = startOfPhase + phase.getDuration() - 1;
        }

        if(startOfPhase > lastSecondOfTraining  || stopOfPhase > lastSecondOfTraining) {
            throw new IllegalArgumentException("Phase duration out of bounds of total training time by " +
                    (stopOfPhase - lastSecondOfTraining) + " second(s).");
        }
        phase.setStart(startOfPhase);
        phase.setStop(stopOfPhase);


    }

    private void setMaxAndMinHr(Phase phase) {
        System.out.println(phase.getStart());
        System.out.println(phase.getStop());
        List<Integer> heartRates = phase.getTraining().getDataPoints().stream()
                .filter(dataPoint -> dataPoint.getRelativeSecond() >= phase.getStart() &&
                        dataPoint.getRelativeSecond() <= phase.getStop())
                .map(DataPoint::getHeartRate)
                .toList();
        phase.setHrMax(Collections.max(heartRates));
        phase.setHrMin(Collections.min(heartRates));
    }
}
