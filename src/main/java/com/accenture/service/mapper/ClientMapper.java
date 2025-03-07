package com.accenture.service.mapper;

import com.accenture.repository.entity.Client;
import com.accenture.service.dto.RequestDTO.ClientRequestDTO;
import com.accenture.service.dto.ResponseDTO.ClientResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel ="Spring", uses = AdresseMapper.class)
public interface ClientMapper {

    Client toClient (ClientRequestDTO clientRequestDTO);
    ClientResponseDTO toClientResponseDTO(Client client);
}
