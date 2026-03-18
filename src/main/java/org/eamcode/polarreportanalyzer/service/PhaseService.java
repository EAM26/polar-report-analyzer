package org.eamcode.polarreportanalyzer.service;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.exception.RecordNotFoundException;
import org.eamcode.polarreportanalyzer.model.Phase;
import org.eamcode.polarreportanalyzer.repository.PhaseRepository;
import org.eamcode.polarreportanalyzer.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
