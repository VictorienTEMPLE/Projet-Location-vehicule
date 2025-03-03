package com.accenture.controller;

import com.accenture.service.UtilitaireService;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Utilitaire")
public class UtilitaireController {
    private final UtilitaireService utilitaireService;

    public UtilitaireController(UtilitaireService utilitaireService) {
        this.utilitaireService = utilitaireService;
    }

    @GetMapping
    List<UtilitaireResponseDTO> voitures() {
        return utilitaireService.liste();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody UtilitaireRequestDTO dto){
        utilitaireService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<UtilitaireResponseDTO> unUtiliraire(@PathVariable("id") int id){
        UtilitaireResponseDTO trouve = utilitaireService.trouver(id);
        return ResponseEntity.ok(trouve);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> delete( int id){
        utilitaireService.supprimer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
