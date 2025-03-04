package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.UtilitaireDAO;
import com.accenture.repository.entity.*;
import com.accenture.repository.entity.Utilitaire;
import com.accenture.repository.entity.Utilitaire;
import com.accenture.repository.entity.Utilitaire;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;
import com.accenture.service.dto.UtilitaireRequestDTO;
import com.accenture.service.dto.UtilitaireResponseDTO;
import com.accenture.service.mapper.UtilitaireMapper;
import com.accenture.shared.FiltreRechercheVehicule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UtilitaireServiceImpl implements UtilitaireService{
    private final UtilitaireDAO utilitaireDAO;
    private final UtilitaireMapper utilitaireMapper;

    public UtilitaireServiceImpl(UtilitaireDAO utilitaireDAO, UtilitaireMapper utilitaireMapper) {
        this.utilitaireDAO = utilitaireDAO;
        this.utilitaireMapper = utilitaireMapper;
    }

    @Override
    public UtilitaireResponseDTO ajouter(UtilitaireRequestDTO dto) {
        Utilitaire utilitaire = utilitaireMapper.toUtilitaire(dto);
        verifierUtilitaire(utilitaire);
        System.out.println(utilitaire);
        Utilitaire utilitaireRetour = utilitaireDAO.save(utilitaire);
        System.out.println(utilitaireRetour);
        return utilitaireMapper.toUtilitaireResponseDTO(utilitaireRetour);
    }

    @Override
    public List<UtilitaireResponseDTO> lister() {
        List<Utilitaire> listeC = utilitaireDAO.findAll();
        return listeC.stream()
                .map(utilitaireMapper::toUtilitaireResponseDTO)
                .toList();
    }

    @Override
    public List<UtilitaireResponseDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException {
        List<Utilitaire> listeUtilitaire = switch (filtreRechercheVehicule){
            case ACTIF -> utilitaireDAO.findByActif(true);
            case RETIREDUPARC -> utilitaireDAO.findByRetireDuParc(true);
        };
        return listeUtilitaire.stream().map(utilitaireMapper::toUtilitaireResponseDTO).toList();
    }

    @Override
    public void supprimer(int id) throws VehiculeException {
        Utilitaire utilitaire = utilitaireDAO.findById(id)
                .orElseThrow(() -> new VehiculeException("Le compte que vous chercher a supprimer n'existe pas..."));
        utilitaireDAO.delete(utilitaire);
    }

    private static void verifierUtilitaire(Utilitaire utilitaire) throws VehiculeException {
        if (utilitaire == null)
            throw new VehiculeException("La utilitaire ne peux pas être null");
        if (utilitaire.getNbBagages() == 0)
            throw new VehiculeException("Le nombre de bagguage doit être superieur à 0");
        if (utilitaire.getCarburant() == null)
            throw new VehiculeException("Le carburant est invalide ou vide");
        if (utilitaire.getNbPlaces()==0)
            throw new VehiculeException("Le nombre de places doit être superieur à 0");
        if (utilitaire.getModele().isEmpty()||utilitaire.getModele().isBlank())
            throw new VehiculeException("Vous devez choisir un model");
        if (utilitaire.getCapacite() ==0)
            throw new VehiculeException("La capacité ne peu pas être nulle");
        if (utilitaire.getChargeMax() == 0)
            throw new VehiculeException("La charge Maximum ne peut-être nulle ou négative");
        if (utilitaire.getPtac() ==0)
            throw new VehiculeException("Le PTAC doit être défini");
        if (utilitaire.getCouleur() == null || utilitaire.getCouleur().isBlank())
            throw new VehiculeException("La couleur est obligatoire");
        if (utilitaire.getClimatisation() == null)
            throw new VehiculeException("La climatisation doit être définie par OUI/NON");
        if (utilitaire.getRetireDuParc() ==null)
            throw new VehiculeException("Il faut définir si la voiture fait parti du parc ou non, elle ne peux être nulle");
        if (utilitaire.getActif() ==null)
            throw new VehiculeException("La voiture dois être définie comme active par OUI/NON");
    }
}
