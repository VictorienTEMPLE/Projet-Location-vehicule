package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;


public interface ClientService {
    public ClientResponseDTO ajouter(ClientRequestDTO dto);
    public ClientResponseDTO trouver(int id) throws ClientException;
    ClientResponseDTO modifier(int id, ClientRequestDTO clientRequestDTO) throws ClientException;
    public void supprimer(int id) throws ClientException;

}
