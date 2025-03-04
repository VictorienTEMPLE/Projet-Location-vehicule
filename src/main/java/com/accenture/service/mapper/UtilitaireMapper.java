package com.accenture.service.mapper;


import com.accenture.repository.entity.Utilitaire;
import com.accenture.repository.entity.Vehicule;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UtilitaireMapper {
    Utilitaire toUtilitaire (UtilitaireRequestDTO utilitaireRequestDTO);
    UtilitaireResponseDTO toUtilitaireResponseDTO(Utilitaire utilitaire);
}
