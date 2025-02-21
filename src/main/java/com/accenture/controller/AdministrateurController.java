package com.accenture.controller;

import com.accenture.service.AdministrateurService;
import com.accenture.service.ClientService;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {
    private final AdministrateurService administrateurService;

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    @GetMapping
    List<AdministrateurResponseDTO> clients() {
        return administrateurService.liste();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDTO dto){
        administrateurService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
