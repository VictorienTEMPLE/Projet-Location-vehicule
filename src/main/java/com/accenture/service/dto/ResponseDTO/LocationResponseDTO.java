package com.accenture.service.dto.ResponseDTO;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Vehicule;
import com.accenture.shared.EtatLocation;

import java.time.LocalDate;

public record LocationResponseDTO(
         Client client,
         Vehicule vehicule,
         //Accessoires accessoires,
         LocalDate dateDeDebut,
         LocalDate dateDeFin,
         int nbkilometreParcourus,
         EtatLocation etatLocation
) {
}
