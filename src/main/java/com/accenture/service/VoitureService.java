package com.accenture.service;


import com.accenture.exception.VoitureException;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;

import java.util.List;

public interface VoitureService {
    public VoitureResponseDTO ajouter(VoitureRequestDTO dto);
    public List<VoitureResponseDTO> liste();
    public VoitureResponseDTO trouver(String Modele) throws VoitureException;
    public VoitureResponseDTO trouverParTypeDeCarburant(String typeCarburant) throws VoitureException;
    public VoitureResponseDTO trouverParTypeNbDePortes(int nbPortes) throws VoitureException;
    public VoitureResponseDTO trouverParTransmission(String Transmission);
    public VoitureResponseDTO modifier(String modele, int nbPlace  , VoitureRequestDTO voitureRequestDTO) throws VoitureException;
    public void supprimer(int id, String modele ) throws VoitureException;
}
