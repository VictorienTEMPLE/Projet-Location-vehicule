package com.accenture.service.mapper;


import com.accenture.repository.entity.Utilitaire;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UtilitaireMapper {
    Utilitaire toUtilitaire (UtilitaireRequestDTO utilitaireRequestDTO);
    UtilitaireResponseDTO toUtilitaireResponseDTO(Utilitaire utilitaire);
}
