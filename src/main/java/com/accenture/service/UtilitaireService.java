package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.RequestDTO.UtilitaireRequestDTO;
import com.accenture.service.dto.ResponseDTO.UtilitaireResponseDTO;
import com.accenture.shared.FiltreRechercheVehicule;


import java.util.List;

public interface UtilitaireService {
    public UtilitaireResponseDTO ajouter(UtilitaireRequestDTO dto);
    public List<UtilitaireResponseDTO> lister();
    public List<UtilitaireResponseDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException;
    public void supprimer(int id ) throws VehiculeException;
}
