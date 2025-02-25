package com.accenture.controller;

import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDTO> clients() {
        return clientService.liste();
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDTO dto){
        clientService.ajouter(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info")
    public ClientResponseDTO info( String email, String password){
    return clientService.trouverClientParEmailEtPassword(email,password);
    }

    @DeleteMapping("/delete")
    ResponseEntity<Void> delete( String email, String password){
        clientService.supprimer(email, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modifier")
    ResponseEntity<ClientResponseDTO> modifier(String email, String password,@RequestBody ClientRequestDTO clientRequestDTO){
        clientService.modifier(email, password, clientRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
