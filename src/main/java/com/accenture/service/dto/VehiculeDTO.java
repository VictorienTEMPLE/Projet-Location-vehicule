package com.accenture.service.dto;

import com.accenture.repository.entity.Voiture;

import java.util.List;

public record VehiculeDTO(
        List<VoitureResponseDTO> listeVoiture,
        List<UtilitaireResponseDTO> listeUtilitaire

) {
}
