package com.accenture.service.dto;

import com.accenture.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;

public record ClientRequestDTO(
         AdresseDTO adresse,
         String nom,
         String prenom,
         LocalDate dateDeNaissance,
         LocalDate dateInscription, // a retirer
         HashSet<Permis>listePermis,
         Boolean desactive,     //a retirer
         String password,
         String email
         ) {
}
