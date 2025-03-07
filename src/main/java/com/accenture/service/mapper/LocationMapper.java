package com.accenture.service.mapper;

import com.accenture.repository.entity.Location;
import com.accenture.service.dto.RequestDTO.LocationRequestDTO;
import com.accenture.service.dto.ResponseDTO.LocationResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface LocationMapper {
    Location toLocation (LocationRequestDTO utilitaireRequestDTO);
    LocationResponseDTO toLocationResponseDTO(Location utilitaire);
}
