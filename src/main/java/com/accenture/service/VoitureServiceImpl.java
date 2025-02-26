package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.exception.VoitureException;

import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.service.mapper.VoitureMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoitureServiceImpl implements VoitureService {
    private final VoitureDAO voitureDAO;
    private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDAO voitureDAO, VoitureMapper voitureMapper) {
        this.voitureDAO = voitureDAO;
        this.voitureMapper = voitureMapper;
    }


    @Override
    public VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO){
        verifierVoiture(voitureRequestDTO);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        System.out.println(voiture);
        Voiture voitureRetour = voitureDAO.save(voiture);
        return voitureMapper.toVoitureResponseDTO(voitureRetour);
    }

    @Override
    public List<VoitureResponseDTO> liste() {
        return List.of();
    }

    @Override
    public VoitureResponseDTO trouver(String Modele) throws VoitureException {
        return null;
    }

    @Override
    public VoitureResponseDTO trouverParTypeDeCarburant(String typeCarburant) throws VoitureException {
        return null;
    }

    @Override
    public VoitureResponseDTO trouverParTypeNbDePortes(int nbPortes) throws VoitureException {
        return null;
    }

    @Override
    public VoitureResponseDTO trouverParTransmission(String Transmission) {
        return null;
    }

    @Override
    public VoitureResponseDTO modifier(String modele, int nbPlace, VoitureRequestDTO voitureRequestDTO) throws VoitureException {
        return null;
    }

    @Override
    public void supprimer(int id, String modele) throws VoitureException {

    }
    private static void verifierVoiture(VoitureRequestDTO voitureRequestDTO) throws VoitureException {
        if (voitureRequestDTO == null)
            throw new VoitureException("Le client ne peux pas être null");
        if (voitureRequestDTO.id() == 0)
            throw new VoitureException("Id non existante");
        if (voitureRequestDTO.nbBagages() == 0)
            throw new VoitureException("Le nombre de bagguage doit être superieur à 0");
        if (voitureRequestDTO.carburant() == null)
            throw new VoitureException("La rue est invalide ou vide");
        if(voitureRequestDTO.carburant().isEmpty())
            throw new VoitureException("Vous devez choisir un type de carburant");
        if (voitureRequestDTO.nbPlaces()==0)
            throw new VoitureException("Le nombre de places doit être superieur à 0");
        if (voitureRequestDTO.modele().isEmpty()||voitureRequestDTO.modele().isBlank())
            throw new VoitureException("Vous devez choisir un model");
        if (voitureRequestDTO.permisB().isEmpty())
            throw new VoitureException("Vous devez absolument avoir un permis correspondant");
    }
}
