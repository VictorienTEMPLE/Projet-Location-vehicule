package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;


public interface ClientService {
    public ClientResponseDTO ajouter(ClientRequestDTO dto);
    public List<ClientResponseDTO> liste();
    public ClientResponseDTO trouver(String email) throws ClientException;
    public ClientResponseDTO modifier(String email, String password, ClientRequestDTO clientRequestDTO) throws ClientException;
    public void supprimer(String email, String password) throws ClientException;



        public ClientResponseDTO trouverClientParEmailEtPassword(String email, String password);
}
