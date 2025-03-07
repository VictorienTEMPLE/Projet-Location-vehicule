package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.RequestDTO.ClientRequestDTO;
import com.accenture.service.dto.ResponseDTO.ClientResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDTO> clients() {
        return clientService.lister();
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

    @DeleteMapping
    ResponseEntity<Void> delete( String email, String password){
        clientService.supprimer(email, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    ResponseEntity<ClientResponseDTO> modifier(String email, String password,@RequestBody ClientRequestDTO clientRequestDTO){
        clientService.modifier(email, password, clientRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
