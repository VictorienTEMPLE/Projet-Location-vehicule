package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.VehiculeDAO;
import com.accenture.repository.entity.Vehicule;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.shared.FiltreRechercheVehicule;

import java.time.LocalDateTime;
import java.util.List;

public class VehiculeServiceImpl implements VehiculeService {
    private final VehiculeDAO vehiculeDAO;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeServiceImpl(VehiculeDAO vehiculeDAO, VehiculeMapper vehiculeMapper) {
        this.vehiculeDAO = vehiculeDAO;
        this.vehiculeMapper = vehiculeMapper;
    }

//    @Override
//    public List<Vehicule> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException {
//        return switch (filtreRechercheVehicule){
//            case LOCATION_DEBUT -> vehiculeDAO.findByCrenauxReservationDebut(LocalDateTime.now());
//            case LOCATION_FIN -> vehiculeDAO.findByCrenauxReservationFin(LocalDateTime.now());
//            default -> throw new VehiculeException("Option invalide");
//        };

        //creer deux liste de utilitaire et voiture, boucler sur la liste
        //pour remplir soit voiture ou utilitaire
        //faire un mapper sur chaque pour recuperer liste respective
        //mettre ses liste dans un vehiculeDTO
    }

