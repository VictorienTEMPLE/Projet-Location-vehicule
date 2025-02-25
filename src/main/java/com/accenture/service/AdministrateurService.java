package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;

import java.util.List;


public interface AdministrateurService {
    public AdministrateurResponseDTO trouverAdminParEmailEtPassword(String email, String password);

    public AdministrateurResponseDTO modifier(String email, String password, AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException;
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws ClientException;
    public List<AdministrateurResponseDTO> listeAdmin();
    public void supprimer(String email, String password) throws AdministrateurException;

    }
