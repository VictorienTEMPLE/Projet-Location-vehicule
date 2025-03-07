package com.accenture.controller;

import com.accenture.service.UtilitaireService;
import com.accenture.service.dto.RequestDTO.UtilitaireRequestDTO;
import com.accenture.service.dto.ResponseDTO.UtilitaireResponseDTO;
import com.accenture.shared.FiltreRechercheVehicule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/Utilitaire")
public class UtilitaireController {
    private final UtilitaireService utilitaireService;

    public UtilitaireController(UtilitaireService utilitaireService) {
        this.utilitaireService = utilitaireService;
    }

    @GetMapping
    List<UtilitaireResponseDTO> utilitaire() {
        List<UtilitaireResponseDTO> listeUtilitaire = utilitaireService.lister();
        log.info("Liste des véhicules utilitaires récupérée avec succès. Nombre de véhicules trouvés : {}", listeUtilitaire.size());
        return listeUtilitaire;

    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody UtilitaireRequestDTO dto){
        log.info("Début de l'ajout d'un véhicule utilitaire avec les données : {}", dto);
        utilitaireService.ajouter(dto);
        log.info("Véhicule utilitaire ajouté avec succès.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{filtreRecherche}")
    ResponseEntity<List<UtilitaireResponseDTO>> trouverParFiltre(FiltreRechercheVehicule filtreRechercheVehicule){
        log.info("Recherche des véhicules utilitaires avec le filtre : {}", filtreRechercheVehicule);
        List<UtilitaireResponseDTO> trouve = utilitaireService.trouver(filtreRechercheVehicule);
        log.info("Véhicules utilitaires trouvés : {}", trouve.size());
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> delete( int id){
        log.info("Tentative de suppression du véhicule utilitaire avec ID : {}", id);
        utilitaireService.supprimer(id);
        log.info("Véhicule utilitaire avec ID : {} a été supprimé avec succès.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
