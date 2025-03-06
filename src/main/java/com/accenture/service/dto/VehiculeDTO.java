package com.accenture.service.dto;


import java.util.ArrayList;
import java.util.List;

public record VehiculeDTO(
        List<VoitureResponseDTO> listeVoiture,
        List<UtilitaireResponseDTO> listeUtilitaire

) {
    public VehiculeDTO() {
      this(new ArrayList<>(),new ArrayList<>());
    }
}
