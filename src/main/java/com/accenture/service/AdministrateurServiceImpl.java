package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrateurServiceImpl implements AdministrateurService{
    private final AdministrateurDAO administrateurDAO;
    private final AdministrateurMapper administrateurMapper;

    public AdministrateurServiceImpl(AdministrateurDAO administrateurDAO, AdministrateurMapper administrateurMapper) {
        this.administrateurDAO = administrateurDAO;
        this.administrateurMapper = administrateurMapper;
    }


    @Override
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws ClientException {
//        verifierClient(clientRequestDTO);
        Administrateur administrateur = administrateurMapper.toAdministrateur(administrateurRequestDTO);
//        client.setDateDInscription(LocalDate.now());
        System.out.println(administrateur);
        Administrateur administrateurRetour = administrateurDAO.save(administrateur);
        return administrateurMapper.toAdministrateurResponseDTO(administrateurRetour);
    }

    @Override
    public List<AdministrateurResponseDTO> liste(){
        List<Administrateur> listeA = administrateurDAO.findAll();
        return listeA.stream()
                .map(administrateurMapper::toAdministrateurResponseDTO)
                .toList();
    }
}
