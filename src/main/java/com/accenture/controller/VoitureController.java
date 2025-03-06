package com.accenture.controller;

import com.accenture.service.VoitureService;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.shared.FiltreRechercheVehicule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
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
        log.info("Début de l'ajout d'un véhicule voiture avec les données : {}", dto);
        voitureService.ajouter(dto);
        log.info("Véhicule voiture ajouté avec succès.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{filtreRecherche}")
    ResponseEntity<List<VoitureResponseDTO>> trouverParFiltre(FiltreRechercheVehicule filtreRechercheVehicule){
        log.info("Recherche des véhicules Voiture avec le filtre : {}", filtreRechercheVehicule);
        List<VoitureResponseDTO> trouve = voitureService.trouver(filtreRechercheVehicule);
        log.info("Véhicules voitures trouvés : {}", trouve.size());
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping
    ResponseEntity<Void> delete( int id){
        log.info("Tentative de suppression du véhicule voiture avec ID : {}", id);
        voitureService.supprimer(id);
        log.info("Véhicule voiture avec ID : {} a été supprimé avec succès.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    ResponseEntity<ClientResponseDTO> modifier(int id,@RequestBody VoitureRequestDTO voitureRequestDTO){
        log.info("Tentative de modification de véhicule voiture avec ID : {}", id);
        voitureService.modifier(id, voitureRequestDTO);
        log.info("Véhicule voiture avec ID : {} a été modifiée avec succès.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
