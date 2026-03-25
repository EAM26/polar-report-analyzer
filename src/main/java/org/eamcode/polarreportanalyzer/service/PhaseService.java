package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.repository.PhaseRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
       Phase phase = modelMapper.mapToPhaseEntity(request);
       phase = setStartAndStop(phase);
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

        Phase phaseUpdated =  phaseRepository.save(modelMapper.updatePhaseFromRequest(request, phase));
        return modelMapper.mapPhaseToResponse(phaseUpdated);
    }

    private Phase setStartAndStop(Phase phase) {
        List<Phase> phases = phase.getTraining().getPhases();
        if(phases.isEmpty()) {
            phase.setStart(0);
            phase.setStop(phase.getDuration() -1);
        } else {
            phase.setStart(phases.getLast().getStop() + 1);
            phase.setStop(phase.getStart() + phase.getDuration() -1);
        }
        return phase;
    }
}
