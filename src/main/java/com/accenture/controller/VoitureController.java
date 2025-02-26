package com.accenture.controller;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.VoitureRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Voiture")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody VoitureRequestDTO dto){
        voitureService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
