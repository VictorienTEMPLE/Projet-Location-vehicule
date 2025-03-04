package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;

import java.util.List;


public interface ClientService {
    public ClientResponseDTO ajouter(ClientRequestDTO dto);
    public List<ClientResponseDTO> lister();
    public ClientResponseDTO trouver(String email) throws ClientException;
    public ClientResponseDTO modifier(String email, String password, ClientRequestDTO clientRequestDTO) throws ClientException;
    public void supprimer(String email, String password) throws ClientException;



        public ClientResponseDTO trouverClientParEmailEtPassword(String email, String password);
}
