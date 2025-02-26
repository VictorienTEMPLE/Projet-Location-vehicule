package com.accenture.service.mapper;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface VoitureMapper {
    Voiture toVoiture (VoitureRequestDTO voitureRequestDTO);
    VoitureResponseDTO toVoitureResponseDTO(Voiture voiture);
}
