package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.LocationDAO;
import com.accenture.repository.UtilitaireDAO;
import com.accenture.repository.entity.Utilitaire;
import com.accenture.service.dto.ResponseDTO.LocationResponseDTO;
import com.accenture.service.dto.RequestDTO.UtilitaireRequestDTO;
import com.accenture.service.dto.ResponseDTO.UtilitaireResponseDTO;
import com.accenture.service.mapper.UtilitaireMapper;
import com.accenture.shared.FiltreRechercheVehicule;
import com.accenture.shared.Permis;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilitaireServiceImpl implements UtilitaireService{
    private final UtilitaireDAO utilitaireDAO;
    private final UtilitaireMapper utilitaireMapper;
    private final LocationDAO locationDAO;

    public UtilitaireServiceImpl(UtilitaireDAO utilitaireDAO, UtilitaireMapper utilitaireMapper, LocationDAO locationDAO) {
        this.utilitaireDAO = utilitaireDAO;
        this.utilitaireMapper = utilitaireMapper;
        this.locationDAO = locationDAO;
    }

    /**
     * Ajoute un nouvel utilitaire à la base de données après l'avoir vérifié.
     *
     * Cette méthode prend un objet {@link UtilitaireRequestDTO}, le convertit en un objet {@link Utilitaire},
     * vérifie sa validité via la méthode {@link #verifierUtilitaire(Utilitaire)}, puis l'enregistre dans la base de données.
     * Si toutes les vérifications sont réussies, l'objet {@link Utilitaire} est enregistré et un objet {@link UtilitaireResponseDTO}
     * est renvoyé, représentant l'utilitaire ajouté.
     *
     * @param dto L'objet {@link UtilitaireRequestDTO} contenant les informations nécessaires à la création d'un nouvel utilitaire.
     * @return Un objet {@link UtilitaireResponseDTO} représentant l'utilitaire qui a été ajouté à la base de données.
     * @throws VehiculeException Si l'utilitaire ne passe pas la validation dans la méthode {@link #verifierUtilitaire(Utilitaire)}.
     */
    @Override
    public UtilitaireResponseDTO ajouter(UtilitaireRequestDTO dto) {
        Utilitaire utilitaire = utilitaireMapper.toUtilitaire(dto);
        verifierUtilitaire(utilitaire);
        Utilitaire utilitaireRetour = utilitaireDAO.save(utilitaire);
        return utilitaireMapper.toUtilitaireResponseDTO(utilitaireRetour);
    }

    /**
     * Récupère la liste de tous les utilitaires enregistrés dans la base de données et les retourne sous forme de DTOs.
     *
     * Cette méthode utilise la méthode {@link UtilitaireDAO#findAll()} pour obtenir tous les objets {@link Utilitaire} présents dans la base de données.
     * Chaque objet {@link Utilitaire} est ensuite transformé en un objet {@link UtilitaireResponseDTO} à l'aide du mapper {@link UtilitaireMapper}.
     * Enfin, une liste de ces DTOs est renvoyée.
     *
     * @return Une liste de {@link UtilitaireResponseDTO} représentant tous les utilitaires enregistrés dans la base de données.
     */
    @Override
    public List<UtilitaireResponseDTO> lister() {
        List<Utilitaire> listeC = utilitaireDAO.findAll();
        return listeC.stream()
                .map(utilitaireMapper::toUtilitaireResponseDTO)
                .toList();
    }

    /**
     * Récupère une liste d'utilitaires basée sur un filtre de recherche spécifié.
     *
     * Cette méthode permet de récupérer des utilitaires selon le filtre spécifié dans le paramètre {@link FiltreRechercheVehicule}.
     * En fonction de ce filtre, la méthode interroge la base de données pour obtenir les utilitaires correspondants.
     * Elle prend en charge deux types de filtres :
     * <ul>
     *     <li>{@link FiltreRechercheVehicule#ACTIF} pour récupérer les utilitaires actifs.</li>
     *     <li>{@link FiltreRechercheVehicule#RETIREDUPARC} pour récupérer les utilitaires retirés du parc.</li>
     * </ul>
     * Si le filtre fourni est incorrect, une exception de type {@link VehiculeException} est levée.
     *
     * @param filtreRechercheVehicule Le filtre de recherche utilisé pour filtrer les utilitaires.
     * @return Une liste de {@link UtilitaireResponseDTO} représentant les utilitaires correspondant au filtre de recherche.
     * @throws VehiculeException Si le filtre spécifié est incorrect ou si aucun utilitaire n'est trouvé pour le filtre donné.
     */
    @Override
    public List<UtilitaireResponseDTO> trouver(FiltreRechercheVehicule filtreRechercheVehicule) throws VehiculeException {
        List<Utilitaire> listeUtilitaire = switch (filtreRechercheVehicule){
            case ACTIF -> utilitaireDAO.findByActif(true);
            case RETIREDUPARC -> utilitaireDAO.findByRetireDuParc(true);
            default -> throw new VehiculeException("Choix incorrect");
        };
        return listeUtilitaire.stream().map(utilitaireMapper::toUtilitaireResponseDTO).toList();
    }

    /**
     * Supprime un utilitaire ou le retire du parc si des locations sont en cours.
     *
     * Cette méthode permet de supprimer un utilitaire de la base de données, ou de le retirer du parc
     * si l'utilitaire est actuellement associé à des locations en cours.
     * Si des locations sont associées à l'utilitaire (c'est-à-dire que l'utilitaire est en cours de location),
     * l'utilitaire sera marqué comme retiré du parc sans être supprimé.
     * Si aucune location n'est associée, l'utilitaire sera définitivement supprimé de la base de données.
     *
     * @param id L'ID de l'utilitaire à supprimer ou retirer du parc.
     * @throws VehiculeException Si l'utilitaire avec l'ID spécifié n'existe pas ou si une erreur survient lors de la suppression.
     */
    @Override
    public void supprimer(int id) throws VehiculeException {
        List<LocationResponseDTO> listeLocation = locationDAO.findByVehiculeId(id);
        Utilitaire utilitaire = utilitaireDAO.findById(id)
                .orElseThrow(() -> new VehiculeException("Le compte que vous chercher a supprimer n'existe pas..."));
        if (listeLocation.isEmpty()) {
            utilitaireDAO.delete(utilitaire);
        }else {
            utilitaire.setRetireDuParc(true);
            utilitaireDAO.save(utilitaire);
        }
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
        if (utilitaire.getPtac() <3.5)
            utilitaire.setPermis(Permis.B);
        else utilitaire.setPermis(Permis.C1);
    }
}
