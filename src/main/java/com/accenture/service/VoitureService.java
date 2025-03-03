package com.accenture.service;


import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;

import java.util.List;

public interface VoitureService {
    public VoitureResponseDTO ajouter(VoitureRequestDTO dto);
    public List<VoitureResponseDTO> liste();
    public VoitureResponseDTO trouver(int id) throws VehiculeException;
    public VoitureResponseDTO modifier(int id, VoitureRequestDTO voitureRequestDTO) throws VehiculeException;
        public void supprimer(int id ) throws VehiculeException;
}
