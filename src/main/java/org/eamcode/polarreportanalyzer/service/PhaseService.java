package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.repository.PhaseRepository;
import org.eamcode.polarreportanalyzer.util.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PhaseService {
    private final ModelMapper modelMapper;
    private final PhaseRepository phaseRepository;

    public PhaseService(ModelMapper modelMapper, PhaseRepository phaseRepository) {
        this.modelMapper = modelMapper;
        this.phaseRepository = phaseRepository;
    }

    public PhaseResponse createPhase(PhaseRequest request) {
       Phase phase = modelMapper.mapToPhaseEntity(request);
       return modelMapper.mapPhaseToResponse(phaseRepository.save(phase));
    }
}
