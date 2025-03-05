package com.accenture.service.dto;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Vehicule;
import com.accenture.shared.EtatLocation;

import java.time.LocalDate;

public record LocationRequestDTO(
        int id,
        String emailClient,
        int idVehicule,
        //Accessoires accessoires,
        LocalDate dateDeDebut,
        LocalDate dateDeFin,
        int nbkilometreParcourus,
        EtatLocation etatLocation
) {
}
