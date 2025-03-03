package com.accenture.controller;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Voiture")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    List<VoitureResponseDTO> voitures() {
        return voitureService.liste();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody VoitureRequestDTO dto){
        voitureService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseDTO> uneVoiture(@PathVariable("id") int id){
        VoitureResponseDTO trouve = voitureService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> delete( int id){
        voitureService.supprimer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modifier")
    ResponseEntity<ClientResponseDTO> modifier(int id,@RequestBody VoitureRequestDTO voitureRequestDTO){
        voitureService.modifier(id, voitureRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
