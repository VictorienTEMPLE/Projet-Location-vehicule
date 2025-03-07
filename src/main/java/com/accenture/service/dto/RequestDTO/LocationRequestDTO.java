package com.accenture.service.dto.RequestDTO;

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
