package com.accenture.controller;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.shared.FiltreRechercheVehicule;
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
        return voitureService.lister();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody VoitureRequestDTO dto){
        voitureService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{filtreRecherche}")
    ResponseEntity<List<VoitureResponseDTO>> trouverParFiltre(FiltreRechercheVehicule filtreRechercheVehicule){
        List<VoitureResponseDTO> trouve = voitureService.trouver(filtreRechercheVehicule);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping
    ResponseEntity<Void> delete( int id){
        voitureService.supprimer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    ResponseEntity<ClientResponseDTO> modifier(int id,@RequestBody VoitureRequestDTO voitureRequestDTO){
        voitureService.modifier(id, voitureRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
