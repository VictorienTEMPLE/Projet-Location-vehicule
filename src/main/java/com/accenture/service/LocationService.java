package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.LocationRequestDTO;
import com.accenture.service.dto.LocationResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.shared.FiltreRechercheVehicule;


import java.time.LocalDate;
import java.util.List;

public interface LocationService {
     LocationResponseDTO ajouter(LocationRequestDTO dto, int idVehicule, String emailClient);
     List<LocationRequestDTO> lister();

    VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate) throws VehiculeException;

     VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate, String categorie) throws VehiculeException;
}
