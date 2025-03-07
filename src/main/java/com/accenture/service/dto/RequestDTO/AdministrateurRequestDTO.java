package com.accenture.service.dto.RequestDTO;

public record AdministrateurRequestDTO(
        String nom,
        String prenom,
        String password,
        String email,
        String fonction) {
}
