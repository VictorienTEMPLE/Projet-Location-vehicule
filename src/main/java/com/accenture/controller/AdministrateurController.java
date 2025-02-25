package com.accenture.controller;

import com.accenture.service.AdministrateurService;
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
        return administrateurService.listeAdmin();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDTO dto){
        administrateurService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info")
    public AdministrateurResponseDTO info(String email, String password){
        return administrateurService.trouverAdminParEmailEtPassword(email,password);
    }
    @PutMapping("/modifier")
    ResponseEntity<AdministrateurResponseDTO> modifier(String email, String password,@RequestBody AdministrateurRequestDTO administrateurRequestDTO){
        administrateurService.modifier(email, password, administrateurRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
