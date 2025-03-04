package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.exception.VehiculeException;

import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.FiltreRechercheVehicule;
import com.accenture.shared.Permis;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        verifierVoiture(voiture);
        System.out.println(voiture);
        Voiture voitureRetour = voitureDAO.save(voiture);
        System.out.println(voitureRetour);
        return voitureMapper.toVoitureResponseDTO(voitureRetour);
    }

    @Override
    public List<VoitureResponseDTO> lister() {
        List<Voiture> listeC = voitureDAO.findAll();
        return listeC.stream()
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();
    }

    @Override
    public List<VoitureResponseDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException {
        List<Voiture> listeVoiture = switch (filtreRechercheVehicule){
            case ACTIF -> voitureDAO.findByActif(true);
            case RETIREDUPARC -> voitureDAO.findByRetireDuParc(true);
            default -> throw new VehiculeException("Option invalide");
        };
        return listeVoiture.stream().map(voitureMapper::toVoitureResponseDTO).toList();
    }

    @Override
    public void supprimer(int id) throws VehiculeException {
        Voiture voiture = voitureDAO.findById(id)
                .orElseThrow(() -> new VehiculeException("Le compte que vous chercher a supprimer n'existe pas..."));
        voitureDAO.delete(voiture);
    }

    @Override
    public VoitureResponseDTO modifier(int id, VoitureRequestDTO voitureRequestDTO) throws VehiculeException {
        Optional<Voiture> optVoiture = voitureDAO.findById(id);
        if(optVoiture.isEmpty()) throw new VehiculeException("Le compte que vous chercher n'existe pas");
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        Voiture voitureExistant = optVoiture.get();
        remplacer(voiture, voitureExistant);
        Voiture voitureEnreg = voitureDAO.save(voitureExistant);
        return voitureMapper.toVoitureResponseDTO(voitureEnreg);
    }

    private static void remplacer(Voiture nouvelleVoiture, Voiture voitureExistante) {
        if(nouvelleVoiture.getCarburant()!=null)
            voitureExistante.setCarburant(nouvelleVoiture.getCarburant());
        if(nouvelleVoiture.getModele()!=null)
            voitureExistante.setModele(nouvelleVoiture.getModele());
        if (nouvelleVoiture.getNbPlaces()!=0)
            voitureExistante.setNbPlaces(nouvelleVoiture.getNbPlaces());
        if (nouvelleVoiture.getMarque() !=null)
            voitureExistante.setMarque(nouvelleVoiture.getMarque());
        if (nouvelleVoiture.getClimatisation() !=null)
            voitureExistante.setCarburant(nouvelleVoiture.getCarburant());
        if (nouvelleVoiture.getKilometrage() !=0)
            voitureExistante.setKilometrage(nouvelleVoiture.getKilometrage());
        if (nouvelleVoiture.getCouleur() !=null)
            voitureExistante.setCouleur(nouvelleVoiture.getCouleur());
    }

    private static void verifierVoiture(Voiture voiture) throws VehiculeException {
        if (voiture == null)
            throw new VehiculeException("La voiture ne peux pas être null");
        if (voiture.getNbBagages() == 0)
            throw new VehiculeException("Le nombre de bagguage doit être superieur à 0");
        if (voiture.getCarburant() == null)
            throw new VehiculeException("Le carburant est invalide ou vide");
        if (voiture.getNbPlaces()==0)
            throw new VehiculeException("Le nombre de places doit être superieur à 0");
        if (voiture.getModele().isEmpty()||voiture.getModele().isBlank())
            throw new VehiculeException("Vous devez choisir un model");
        if (voiture.getMarque() == null || voiture.getMarque().isBlank())
            throw new VehiculeException("La marque doit être définie");
        if (voiture.getCouleur()==null|| voiture.getCouleur().isBlank())
            throw new VehiculeException("La couleur doit être définie");
        if (voiture.getKilometrage()==0)
            throw new VehiculeException("Le killométrage ne peut-être null ou négatif");
        if (voiture.getNbPortes()==0)
            throw new VehiculeException("Le nombre de Portes doit être défini");
        if (voiture.getTarifJournalierDeBase() ==0)
            throw new VehiculeException("Le tarif journalier est obligatoire");
        if (voiture.getNbPlaces()<10) voiture.setPermis(Permis.B);
        else voiture.setPermis(Permis.DE);
        if (voiture.getClimatisation() ==null)
            throw new VehiculeException("La climatisation doit être définie par Oui/non");
        if (voiture.getRetireDuParc() == null)
            throw new VehiculeException("Il faut définir si la voiture fait parti du parc ou non, elle ne peux être nulle");
        if (voiture.getActif() == null)
            throw new VehiculeException("La voiture dois être définie comme active par OUI/NON");
    }
}
