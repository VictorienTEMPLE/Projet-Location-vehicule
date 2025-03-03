package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.Utilitaire;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;


import java.util.List;

public interface UtilitaireService {
    public UtilitaireResponseDTO ajouter(UtilitaireRequestDTO dto);
    public List<UtilitaireResponseDTO> liste();
    public UtilitaireResponseDTO trouver(int id) throws VehiculeException;
    public void supprimer(int id ) throws VehiculeException;
}
