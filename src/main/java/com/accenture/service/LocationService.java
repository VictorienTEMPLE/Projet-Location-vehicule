package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.LocationRequestDTO;
import com.accenture.service.dto.LocationResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.shared.FiltreRechercheVehicule;

import java.time.LocalDate;
import java.util.List;

public interface LocationService {
    public LocationResponseDTO ajouter(LocationRequestDTO dto, int idVehicule, String emailClient);
    public List<LocationRequestDTO> lister();
    public VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate) throws VehiculeException;
    public void supprimer(int id ) throws VehiculeException;
}
