package org.eamcode.polarreportanalyzer.controller;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.service.PhaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/phases")
public class PhaseController {

    private final PhaseService phaseService;

    public PhaseController(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    @PostMapping
    public ResponseEntity<PhaseResponse> createPhase(@RequestBody PhaseRequest request) {
        System.out.println("Received PhaseRequest: " + request);
        PhaseResponse createdPhase = phaseService.createPhase(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(createdPhase.id())
                .toUri();
        return ResponseEntity.created(location).body(createdPhase);
    }

    @GetMapping
    public ResponseEntity<List<PhaseResponse>> getAllPhases() {
        return ResponseEntity.ok(phaseService.getAllPhases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhaseResponse> getPhaseById(@PathVariable long id) {
        return ResponseEntity.ok(phaseService.getPhaseById(id));
    }
}