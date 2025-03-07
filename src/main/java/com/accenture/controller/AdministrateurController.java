package com.accenture.controller;

import com.accenture.service.AdministrateurService;
import com.accenture.service.dto.RequestDTO.AdministrateurRequestDTO;
import com.accenture.service.dto.ResponseDTO.AdministrateurResponseDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {
    private final AdministrateurService administrateurService;



    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    @GetMapping
    List<AdministrateurResponseDTO> administrateur() {
        List<AdministrateurResponseDTO> listeAdministrateur = administrateurService.listerAdmin();
        log.info("Liste des administrateurs récupérée avec succès. Nombre d'administrateurs : {}", listeAdministrateur.size());
        return listeAdministrateur;


    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDTO dto){
        log.info("Administrateur ajouté avec succès : {}", dto);
        administrateurService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info")
    public AdministrateurResponseDTO info(String email, String password){
        log.info("Administrateur demandé : ");
        AdministrateurResponseDTO infoAdministrateur = administrateurService.trouverAdminParEmailEtPassword(email, password);
        log.info("Administrateur trouvé : {}", infoAdministrateur);
        return infoAdministrateur;
    }
    @PatchMapping
    ResponseEntity<AdministrateurResponseDTO> modifier(String email, String password,@RequestBody AdministrateurRequestDTO administrateurRequestDTO){
        log.info("Administrateur avec email {}  modifié avec succès.", email);
        administrateurService.modifier(email, password, administrateurRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
