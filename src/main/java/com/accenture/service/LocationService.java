package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.LocationDTO;
import com.accenture.shared.FiltreRechercheVehicule;

import java.util.List;

public interface LocationService {
    public LocationDTO ajouter(LocationDTO dto);
    public List<LocationDTO> lister();
    public List<LocationDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException;
    public void supprimer(int id ) throws VehiculeException;
}
