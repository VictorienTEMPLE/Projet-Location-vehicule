package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.LocationDAO;
import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.LocationResponseDTO;
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
    private final LocationDAO locationDAO;

    public VoitureServiceImpl(VoitureDAO voitureDAO, VoitureMapper voitureMapper, LocationDAO locationDAO) {
        this.voitureDAO = voitureDAO;
        this.voitureMapper = voitureMapper;
        this.locationDAO = locationDAO;
    }

    /**
     * Ajoute une nouvelle voiture à la base de données.
     *
     * Cette méthode permet d'ajouter une nouvelle voiture à la base de données après avoir validé les informations
     * fournies via le DTO `VoitureRequestDTO`. La voiture est ensuite enregistrée dans le système et un DTO
     * de réponse `VoitureResponseDTO` est retourné.
     *
     * @param voitureRequestDTO Le DTO contenant les informations de la voiture à ajouter.
     * @return Un objet `VoitureResponseDTO` contenant les détails de la voiture enregistrée.
     * @throws VehiculeException Si les informations de la voiture sont invalides ou si une erreur survient lors de l'ajout.
     */
    @Override
    public VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO){
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        verifierVoiture(voiture);
        Voiture voitureRetour = voitureDAO.save(voiture);
        return voitureMapper.toVoitureResponseDTO(voitureRetour);
    }

    /**
     * Récupère la liste de toutes les voitures enregistrées.
     *
     * Cette méthode permet de récupérer toutes les voitures présentes dans la base de données,
     * puis de les convertir en une liste de DTOs `VoitureResponseDTO` avant de les retourner.
     *
     * @return Une liste de DTOs `VoitureResponseDTO` représentant toutes les voitures enregistrées.
     */
    @Override
    public List<VoitureResponseDTO> lister() {
        List<Voiture> listeC = voitureDAO.findAll();
        return listeC.stream()
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();
    }
    /**
     * Recherche et récupère une liste de voitures en fonction du filtre spécifié.
     *
     * Cette méthode permet de récupérer une liste de voitures en fonction d'un filtre de recherche donné.
     * Les filtres disponibles sont :
     * - **ACTIF** : Récupère les voitures actives.
     * - **RETIREDUPARC** : Récupère les voitures retirées du parc.
     *
     * @param filtreRechercheVehicule Le filtre de recherche à appliquer. Il peut être de type `ACTIF` ou `RETIREDUPARC`.
     * @return Une liste de DTOs `VoitureResponseDTO` représentant les voitures correspondant au filtre de recherche.
     * @throws VehiculeException Si le filtre fourni est invalide ou si une erreur survient lors de la recherche des voitures.
     */
    @Override
    public List<VoitureResponseDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException {
        List<Voiture> listeVoiture = switch (filtreRechercheVehicule){
            case ACTIF -> voitureDAO.findByActif(true);
            case RETIREDUPARC -> voitureDAO.findByRetireDuParc(true);
            default -> throw new VehiculeException("Option invalide");
        };
        return listeVoiture.stream().map(voitureMapper::toVoitureResponseDTO).toList();
    }

    /**
     * Supprime une voiture ou marque une voiture comme retirée du parc en fonction de son état.
     *
     * Cette méthode permet de supprimer une voiture du système si aucune location n'est associée à celle-ci.
     * Si la voiture est actuellement en location, elle est simplement marquée comme "retirée du parc" et non supprimée.
     * Une exception est lancée si la voiture n'existe pas dans la base de données.
     *
     * @param id L'identifiant de la voiture à supprimer.
     * @throws VehiculeException Si la voiture avec l'ID spécifié n'existe pas ou si une erreur survient lors de la suppression.
     */
    @Override
    public void supprimer(int id) throws VehiculeException {
        List<LocationResponseDTO> listeLocation = locationDAO.findByVehiculeId(id);
        Voiture voiture = voitureDAO.findById(id)
                .orElseThrow(() -> new VehiculeException("Le compte que vous chercher a supprimer n'existe pas..."));
        if (listeLocation.isEmpty()) {
            voitureDAO.delete(voiture);
        }else {
            voiture.setRetireDuParc(true);
            voitureDAO.save(voiture);
        }
    }
    /**
     * Modifie les informations d'une voiture existante dans la base de données.
     *
     * Cette méthode permet de mettre à jour les détails d'une voiture en fonction de son identifiant.
     * Si la voiture avec l'ID spécifié n'existe pas dans la base de données, une exception est levée.
     * Si la voiture existe, ses données sont mises à jour avec celles fournies dans le DTO `voitureRequestDTO`.
     *
     * @param id L'identifiant de la voiture à modifier.
     * @param voitureRequestDTO L'objet contenant les nouvelles informations de la voiture.
     * @return Un objet {@link VoitureResponseDTO} représentant la voiture mise à jour.
     * @throws VehiculeException Si la voiture avec l'ID spécifié n'existe pas dans la base de données.
     */
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
