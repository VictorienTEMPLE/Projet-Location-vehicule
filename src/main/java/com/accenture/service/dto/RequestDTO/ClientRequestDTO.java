package com.accenture.service.dto.RequestDTO;

import com.accenture.service.dto.AdresseDTO;
import com.accenture.shared.Permis;

import java.time.LocalDate;

public record ClientRequestDTO(
         AdresseDTO adresse,
         String nom,
         String prenom,
         LocalDate dateDeNaissance,
         LocalDate dateInscription, // a retirer
         Permis listePermis,
         Boolean desactive,     //a retirer
         String password,
         String email
         ) {
}
