package com.accenture.controller;

import com.accenture.service.LocationService;

import com.accenture.service.dto.LocationRequestDTO;
import com.accenture.service.dto.LocationResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.shared.FiltreRechercheVehicule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/Location")
public class LocationController {
    public LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody LocationRequestDTO dto, int idVehicule, String emailClient){
        locationService.ajouter(dto,idVehicule,emailClient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{filtreRecherche}")
    ResponseEntity<VehiculeDTO> trouverParFiltre(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate){
        VehiculeDTO trouve = locationService.trouver(filtreRechercheVehicule,localDate);
        return ResponseEntity.ok(trouve);
    }
}
