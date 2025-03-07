package com.accenture.controller;

import com.accenture.service.LocationService;

import com.accenture.service.dto.RequestDTO.LocationRequestDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.shared.FiltreRechercheVehicule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {
    public LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody LocationRequestDTO dto, int idVehicule, String emailClient){
        log.info("Demande d'ajout de location : Véhicule ID: {}, Client Email: {}", idVehicule, emailClient);
        locationService.ajouter(dto,idVehicule,emailClient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

        //gerer type vehicule et faire javadoc
    @GetMapping()
    ResponseEntity<VehiculeDTO> trouverParFiltre(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate, String categorie){
        log.info("Recherche de véhicules avec le filtre : {} pour la date : {} et la catégorie : {}",  filtreRechercheVehicule, localDate, categorie);
                VehiculeDTO trouve = locationService.trouver(filtreRechercheVehicule,localDate, categorie);
        log.info("Véhicules trouvés pour le filtre : {} et la catégorie : {}", filtreRechercheVehicule, categorie);

        return ResponseEntity.ok(trouve);
    }
}
