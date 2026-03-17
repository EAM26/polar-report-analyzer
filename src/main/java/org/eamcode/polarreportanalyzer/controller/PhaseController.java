package org.eamcode.polarreportanalyzer.controller;

import org.eamcode.polarreportanalyzer.dto.PhaseRequest;
import org.eamcode.polarreportanalyzer.dto.PhaseResponse;
import org.eamcode.polarreportanalyzer.service.PhaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
                .path("/{id}")
                .buildAndExpand(createdPhase.id())
                .toUri();
        return ResponseEntity.created(location).body(createdPhase);
    }
}