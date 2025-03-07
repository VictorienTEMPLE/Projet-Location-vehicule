package com.accenture.service.dto.ResponseDTO;

import com.accenture.service.dto.AdresseDTO;
import com.accenture.shared.Permis;

import java.time.LocalDate;

public record ClientResponseDTO(
        AdresseDTO adresse,
        String nom,
        String prenom,
        LocalDate dateDeNaissance,
        LocalDate dateInscription,
        Permis listePermis,
        Boolean desactive,
        String email
) {
}
