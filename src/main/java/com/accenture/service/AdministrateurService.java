package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientRequestDTO;

import java.util.List;


public interface AdministrateurService {
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws ClientException;
    public List<AdministrateurResponseDTO> liste();
}
