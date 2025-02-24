package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;

import java.util.List;
import java.util.Optional;


public interface ClientService {
    public ClientResponseDTO ajouter(ClientRequestDTO dto);
    public List<ClientResponseDTO> liste();
    public ClientResponseDTO trouver(String email) throws ClientException;
    ClientResponseDTO modifier(String email, ClientRequestDTO clientRequestDTO) throws ClientException;
    public void supprimer(String email) throws ClientException;


    public ClientResponseDTO trouverUserParEmailEtPassword(String email, String password);
}
