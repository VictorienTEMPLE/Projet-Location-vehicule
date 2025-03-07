package com.accenture.service.mapper;

import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.RequestDTO.AdministrateurRequestDTO;
import com.accenture.service.dto.ResponseDTO.AdministrateurResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface AdministrateurMapper {
    Administrateur toAdministrateur (AdministrateurRequestDTO administrateurRequestDTO);
    AdministrateurResponseDTO toAdministrateurResponseDTO(Administrateur administrateur);
}
