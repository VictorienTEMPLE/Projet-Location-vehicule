package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.LocationDAO;
import com.accenture.repository.VehiculeDAO;
import com.accenture.repository.entity.*;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.LocationMapper;
import com.accenture.service.mapper.UtilitaireMapper;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.FiltreRechercheVehicule;
import com.accenture.shared.Permis;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {
    private final LocationDAO locationDAO;
    private final ClientDAO clientDAO;
    private final VehiculeDAO vehiculeDAO;
    private final VehiculeMapper vehiculeMapper;
    private final VoitureMapper voitureMapper;
    private final UtilitaireMapper utilitaireMapper;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationDAO locationDAO, ClientDAO clientDAO, VehiculeDAO vehiculeDAO, VehiculeMapper vehiculeMapper, VoitureMapper voitureMapper, UtilitaireMapper utilitaireMapper, LocationMapper locationMapper) {
        this.locationDAO = locationDAO;
        this.clientDAO = clientDAO;
        this.vehiculeDAO = vehiculeDAO;
        this.vehiculeMapper = vehiculeMapper;
        this.voitureMapper = voitureMapper;
        this.utilitaireMapper = utilitaireMapper;
        this.locationMapper = locationMapper;
    }

@Override
    public LocationResponseDTO ajouter(LocationRequestDTO dto, int idVehicule, String emailClient){
        Location location = locationMapper.toLocation(dto);
        Client client = clientDAO.findByEmail(emailClient).orElseThrow(()->new LocationException("Le client est introuvable"));
        Vehicule vehicule = vehiculeDAO.findById(idVehicule).orElseThrow(()->new LocationException("Le véhicule est introuvable"));
        location.setClient(client);
        location.setVehicule(vehicule);
        verifierLocation(location);
        Location locationRetour = locationDAO.save(location);
        return locationMapper.toLocationResponseDTO(locationRetour);
    }

    @Override
    public List<LocationRequestDTO> lister() {
        return List.of();
    }

    @Override
    public VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate) throws VehiculeException{
        List<LocationResponseDTO> listeLocations = switch (filtreRechercheVehicule){
            case LOCATION_DEBUT -> locationDAO.findByDateDeDebut(localDate);
            case LOCATION_FIN -> locationDAO.findByDateDeFin(localDate);
            default -> throw new VehiculeException("Option invalide");
        };


        VehiculeDTO vehiculeDTO = new VehiculeDTO(new ArrayList<>(),new ArrayList<>());
        List<VoitureResponseDTO> listeVoiture = listeLocations.stream()
                .filter(l->l.vehicule() instanceof Voiture)
                .map(l->(Voiture) l.vehicule())
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();

        vehiculeDTO.listeVoiture().addAll(listeVoiture);


        List<UtilitaireResponseDTO> listeUtilitaire = listeLocations.stream()
                .filter(l->l.vehicule() instanceof Utilitaire)
                .map(l->(Utilitaire) l.vehicule())
                .map(utilitaireMapper::toUtilitaireResponseDTO)
                .toList();

        vehiculeDTO.listeUtilitaire().addAll(listeUtilitaire);

        return vehiculeDTO;
    }

    @Override
    public void supprimer(int id ) throws VehiculeException{

    }//parcourir la location
    private static void verifierLocation(Location location) throws VehiculeException {
        if (location == null)
            throw new LocationException("La la location ne peux pas être null");
        if (location.getDateDeDebut() == null)
            throw new LocationException("La date de début ne peux pas être nulle");
        if (location.getDateDeFin() == null)
            throw new LocationException("La date de fin ne peux pas être nulle");
        if (location.getClient() == null)
            throw new LocationException("Le client ne peux pas être null");
        if (location.getVehicule() ==null)
            throw new LocationException("Le véhicule ne peux pas être null");
        if (location.getNbkilometreParcourus() ==0)
            throw new LocationException("Le killométrage ne peux pas être null");
        if (location.getEtatLocation()==null)
            throw new LocationException("L'état de location doit être défini");

        int montantTotal = (int)(location.getVehicule().getTarifJournalierDeBase()*(ChronoUnit.DAYS.between(location.getDateDeDebut(),location.getDateDeFin())+1));
        location.setMontantTotalCalcule(montantTotal);
    }


}
