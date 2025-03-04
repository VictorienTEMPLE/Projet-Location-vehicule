package com.accenture.service.mapper;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Vehicule;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface VoitureMapper {
    Voiture toVoiture (VoitureRequestDTO voitureRequestDTO);
    VoitureResponseDTO toVoitureResponseDTO(Voiture voiture);
    List<VehiculeDTO> toVehiculeDTO(List<Vehicule> vehicules);
}
