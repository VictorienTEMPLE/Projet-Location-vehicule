package com.accenture.service.dto;

import com.accenture.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;

public record AdministrateurResponseDTO(
        String nom,
        String prenom,
        String email,
        String fonction
) {
}
