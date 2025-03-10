package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.RequestDTO.AdministrateurRequestDTO;
import com.accenture.service.dto.ResponseDTO.AdministrateurResponseDTO;

import java.util.List;


public interface AdministrateurService {
    public AdministrateurResponseDTO trouverAdminParEmailEtPassword(String email, String password);

    public AdministrateurResponseDTO modifier(String email, String password, AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException;
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws ClientException;
    public List<AdministrateurResponseDTO> listerAdmin();
    public void supprimer(String email, String password) throws AdministrateurException;

    }
