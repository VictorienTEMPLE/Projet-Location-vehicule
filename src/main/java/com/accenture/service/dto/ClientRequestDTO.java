package com.accenture.service.dto;

import com.accenture.repository.entity.Adresse;
import com.accenture.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;

public record ClientRequestDTO(
         Adresse adresse,
         String nom,
         String prenom,
         LocalDate dateDeNaissance,
         LocalDate dateInscription,
         HashSet<Permis>listePermis,
         Boolean desactive,
         String password,
         String email
         ) {
}
