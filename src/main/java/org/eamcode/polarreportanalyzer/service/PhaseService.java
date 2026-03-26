package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseIntervalRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.DataPoint;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.repository.PhaseRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        int lastSecondOfTraining = phase.getTraining().getDataPoints().getLast().getRelativeSecond();
//        List<Phase> phases = phase.getTraining().getPhases();
        Optional<Phase> previousPhase = phaseRepository.findTopByTrainingIdOrderByStopDesc(phase.getTraining().getId());
        int startOfPhase;
        int stopOfPhase;
        if (previousPhase.isEmpty()) {
            startOfPhase = 0;
            stopOfPhase = phase.getDuration() -1;
        } else {
            startOfPhase = previousPhase.get().getStop() + 1;
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
        List<Integer> heartRates = phase.getTraining().getDataPoints().stream()
                .filter(dataPoint -> dataPoint.getRelativeSecond() >= phase.getStart() &&
                        dataPoint.getRelativeSecond() <= phase.getStop() && dataPoint.getHeartRate() != null)
                .map(DataPoint::getHeartRate)
                .toList();
        if(!heartRates.isEmpty()) {
            phase.setHrMax(Collections.max(heartRates));
            phase.setHrMin(Collections.min(heartRates));
        }
    }

    @Transactional
    public List<PhaseResponse> createInterval(PhaseIntervalRequest request) {
        List<PhaseResponse> responses = new ArrayList<>();
        for (int i = 0; i < request.factor(); i++) {
            for(PhaseRequest singlePhase: request.requests()) {
                responses.add(createPhase(singlePhase));
            }
        }
        return responses;
    }
}
