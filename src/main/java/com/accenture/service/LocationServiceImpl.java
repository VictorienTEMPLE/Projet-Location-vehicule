package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.LocationDAO;
import com.accenture.repository.VehiculeDAO;
import com.accenture.repository.entity.*;
import com.accenture.service.dto.*;
import com.accenture.service.dto.RequestDTO.LocationRequestDTO;
import com.accenture.service.dto.ResponseDTO.LocationResponseDTO;
import com.accenture.service.dto.ResponseDTO.UtilitaireResponseDTO;
import com.accenture.service.dto.ResponseDTO.VoitureResponseDTO;
import com.accenture.service.mapper.LocationMapper;
import com.accenture.service.mapper.UtilitaireMapper;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.FiltreRechercheVehicule;
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
    private final VoitureMapper voitureMapper;
    private final UtilitaireMapper utilitaireMapper;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationDAO locationDAO, ClientDAO clientDAO, VehiculeDAO vehiculeDAO, VehiculeMapper vehiculeMapper, VoitureMapper voitureMapper, UtilitaireMapper utilitaireMapper, LocationMapper locationMapper) {
        this.locationDAO = locationDAO;
        this.clientDAO = clientDAO;
        this.vehiculeDAO = vehiculeDAO;
        this.voitureMapper = voitureMapper;
        this.utilitaireMapper = utilitaireMapper;
        this.locationMapper = locationMapper;
    }
    /**
     * Ajoute une nouvelle location de véhicule pour un client spécifique.
     *
     * Cette méthode crée une nouvelle location à partir des informations fournies dans un objet {@link LocationRequestDTO}. Elle associe cette location
     * à un client et un véhicule, après avoir validé l'existence du client et du véhicule dans la base de données. Si le client ou le véhicule n'est pas trouvé,
     * une exception {@link LocationException} est lancée. Ensuite, la location est enregistrée dans la base de données, et un objet {@link LocationResponseDTO}
     * représentant la location ajoutée est retourné.
     *
     * @param dto L'objet {@link LocationRequestDTO} contenant les informations de la location à ajouter. Ne peut pas être null.
     * @param idVehicule L'identifiant du véhicule à associer à la location. Ne peut pas être null.
     * @param emailClient L'email du client qui effectue la location. Ne peut pas être null.
     * @return Un objet {@link LocationResponseDTO} représentant la location ajoutée, après enregistrement dans la base de données.
     * @throws LocationException Si le client ou le véhicule n'est pas trouvé dans la base de données, ou si une erreur se produit lors de l'ajout de la location.
     */
    @Override
    public LocationResponseDTO ajouter(LocationRequestDTO dto, int idVehicule, String emailClient) {
        Location location = locationMapper.toLocation(dto);
        Client client = clientDAO.findByEmail(emailClient).orElseThrow(() -> new LocationException("Le client est introuvable"));
        Vehicule vehicule = vehiculeDAO.findById(idVehicule).orElseThrow(() -> new LocationException("Le véhicule est introuvable"));
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

    /**
     * Recherche des véhicules (voitures et utilitaires) en fonction d'un filtre de recherche et d'une date donnée.
     *
     * Cette méthode permet de récupérer les véhicules associés à des locations qui commencent ou qui se terminent à une date donnée,
     * en fonction du filtre de recherche spécifié. Les véhicules sont filtrés par type (Voiture ou Utilitaire) et sont ajoutés à des listes
     * distinctes dans l'objet {@link VehiculeDTO}. Si le filtre est invalide, une exception {@link VehiculeException} est lancée.
     *
     * @param filtreRechercheVehicule Le filtre de recherche pour déterminer le type de date à rechercher (date de début ou de fin de location).
     *                                Ne peut pas être null.
     * @param localDate La date utilisée pour effectuer la recherche, soit la date de début, soit la date de fin d'une location. Ne peut pas être null.
     * @return Un objet {@link VehiculeDTO} contenant les listes de véhicules de type Voiture et Utilitaire trouvés, basées sur les locations et la date spécifiée.
     *         Ne peut pas être null.
     * @throws VehiculeException Si le filtre de recherche est invalide ou si un problème survient lors de la récupération des véhicules.
     */
    @Override
    public VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate) throws VehiculeException {
        List<LocationResponseDTO> listeLocations = switch (filtreRechercheVehicule) {
            case LOCATION_DEBUT -> locationDAO.findByDateDeDebut(localDate);
            case LOCATION_FIN -> locationDAO.findByDateDeFin(localDate);
            default -> throw new VehiculeException("Option invalide");
        };

        VehiculeDTO vehiculeDTO = new VehiculeDTO(new ArrayList<>(), new ArrayList<>());
        List<VoitureResponseDTO> listeVoiture = listeLocations.stream()
                .filter(l -> l.vehicule() instanceof Voiture)
                .map(l -> (Voiture) l.vehicule())
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();

        vehiculeDTO.listeVoiture().addAll(listeVoiture);


        List<UtilitaireResponseDTO> listeUtilitaire = listeLocations.stream()
                .filter(l -> l.vehicule() instanceof Utilitaire)
                .map(l -> (Utilitaire) l.vehicule())
                .map(utilitaireMapper::toUtilitaireResponseDTO)
                .toList();

        vehiculeDTO.listeUtilitaire().addAll(listeUtilitaire);

        return vehiculeDTO;
    }
    /**
     * Recherche des véhicules de type spécifique (Voiture ou Utilitaire) en fonction d'un filtre de recherche, d'une date et d'une catégorie spécifiée.
     *
     * Cette méthode permet de récupérer des véhicules associés à des locations qui commencent ou qui se terminent à une date donnée,
     * en fonction du filtre de recherche et de la catégorie spécifiée (Voiture ou Utilitaire). Elle permet de filtrer les véhicules selon la catégorie
     * et d'ajouter les véhicules trouvés dans l'objet {@link VehiculeDTO}. Si le filtre ou la catégorie est invalide, une exception {@link VehiculeException} est lancée.
     *
     * @param filtreRechercheVehicule Le filtre de recherche pour déterminer le type de date à rechercher (date de début ou de fin de location).
     *                                Ne peut pas être null.
     * @param localDate La date utilisée pour effectuer la recherche, soit la date de début, soit la date de fin d'une location. Ne peut pas être null.
     * @param categorie La catégorie de véhicule à rechercher. Elle doit être soit "Voiture" soit "Utilitaire". Ne peut pas être null.
     * @return Un objet {@link VehiculeDTO} contenant les véhicules trouvés de la catégorie spécifiée.
     *         Si aucun véhicule n'est trouvé pour la catégorie spécifiée, la liste correspondante dans le DTO sera vide. Ne peut pas être null.
     * @throws VehiculeException Si le filtre de recherche ou la catégorie est invalide, ou si un problème survient lors de la récupération des véhicules.
     */
    @Override
    public VehiculeDTO trouver(FiltreRechercheVehicule filtreRechercheVehicule, LocalDate localDate, String categorie) throws VehiculeException {
        List<LocationResponseDTO> listeLocations = switch (filtreRechercheVehicule) {
            case LOCATION_DEBUT -> locationDAO.findByDateDeDebut(localDate);
            case LOCATION_FIN -> locationDAO.findByDateDeFin(localDate);
            default -> throw new VehiculeException("Option invalide");
        };

        VehiculeDTO vehiculeDTO = new VehiculeDTO();

        switch (categorie) {
            case "Voiture" -> {
                List<VoitureResponseDTO> listeVoiture = listeLocations.stream()
                        .filter(l -> l.vehicule()instanceof Voiture)
                        .map(l -> voitureMapper.toVoitureResponseDTO((Voiture) l.vehicule()))
                        .distinct()
                        .toList();
                vehiculeDTO.listeVoiture().addAll(listeVoiture);
            }
            case "Utilitaire" -> {
                List<UtilitaireResponseDTO> listeUtilitaire = listeLocations.stream()
                        .filter(l -> l.vehicule()instanceof Utilitaire)
                        .map(l -> utilitaireMapper.toUtilitaireResponseDTO((Utilitaire) l.vehicule()))
                        .distinct()
                        .toList();
                vehiculeDTO.listeUtilitaire().addAll(listeUtilitaire);
            }
            default -> throw new VehiculeException("Catégorie Invalide");
        }
        return vehiculeDTO;
    }


    private static void verifierLocation(Location location) throws VehiculeException {
        if (location == null)
            throw new LocationException("La la location ne peux pas être null");
        if (location.getDateDeDebut() == null)
            throw new LocationException("La date de début ne peux pas être nulle");
        if (location.getDateDeFin() == null)
            throw new LocationException("La date de fin ne peux pas être nulle");
        if (location.getClient() == null)
            throw new LocationException("Le client ne peux pas être null");
        if (location.getVehicule() == null)
            throw new LocationException("Le véhicule ne peux pas être null");
        if (location.getNbkilometreParcourus() == 0)
            throw new LocationException("Le killométrage ne peux pas être null");
        if (location.getEtatLocation() == null)
            throw new LocationException("L'état de location doit être défini");

        int montantTotal = (int) (location.getVehicule().getTarifJournalierDeBase() * (ChronoUnit.DAYS.between(location.getDateDeDebut(), location.getDateDeFin()) + 1));
        location.setMontantTotalCalcule(montantTotal);
    }


}
