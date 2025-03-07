package com.accenture.service.dto;


import com.accenture.service.dto.ResponseDTO.UtilitaireResponseDTO;
import com.accenture.service.dto.ResponseDTO.VoitureResponseDTO;

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
