package com.accenture.service.mapper;

import com.accenture.repository.entity.Utilitaire;
import com.accenture.repository.entity.Vehicule;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.UtilitaireResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class VehiculeMapper {
    //creer deux liste de utilitaire et voiture, boucler sur la liste
    //pour remplir soit voiture ou utilitaire
    //faire un mapper sur chaque pour recuperer liste respective
    //mettre ses liste dans un vehiculeDTO

    private final VoitureMapper voitureMapper;
    private final UtilitaireMapper utilitaireMapper;

    public VehiculeMapper(VoitureMapper voitureMapper, UtilitaireMapper utilitaireMapper) {
        this.voitureMapper = voitureMapper;
        this.utilitaireMapper = utilitaireMapper;
    }

    public VehiculeDTO toDTO(List<Vehicule> vehicules){
        List<VoitureResponseDTO> listeVoiture = vehicules.stream()
                .filter(v-> v.getClass().getName().equals("Voiture"))
                .map(v-> (Voiture)v)
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();

        List<UtilitaireResponseDTO> listeUtilitaire = vehicules.stream()
                .filter(u-> u.getClass().getName().equals("Utilitaire"))
                .map(u-> (Utilitaire)u)
                .map(utilitaireMapper::toUtilitaireResponseDTO)
                .toList();

        return new VehiculeDTO(listeVoiture,listeUtilitaire);
    }

}
