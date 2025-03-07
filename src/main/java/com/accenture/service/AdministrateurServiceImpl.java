package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.RequestDTO.AdministrateurRequestDTO;
import com.accenture.service.dto.ResponseDTO.AdministrateurResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdministrateurServiceImpl implements AdministrateurService {
    private final AdministrateurDAO administrateurDAO;
    private final AdministrateurMapper administrateurMapper;
    private static final String REGEX_MDP = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public AdministrateurServiceImpl(AdministrateurDAO administrateurDAO, AdministrateurMapper administrateurMapper) {
        this.administrateurDAO = administrateurDAO;
        this.administrateurMapper = administrateurMapper;
    }

    /**
     * Ajoute un nouvel administrateur à la base de données.
     *
     * Cette méthode commence par vérifier la validité des informations de l'administrateur à l'aide de la méthode verifierAdmin.
     * Ensuite, elle mappe les informations de l'objet {@link AdministrateurRequestDTO} vers un objet {@link Administrateur}.
     * Le nouvel administrateur est ensuite enregistré dans la base de données. Enfin, un objet {@link AdministrateurResponseDTO} représentant l'administrateur ajouté est retourné.
     *
     * @param administrateurRequestDTO L'objet contenant les informations de l'administrateur à ajouter. Ne peut pas être null.
     * @return Un objet {@link AdministrateurResponseDTO} représentant l'administrateur ajouté, une fois enregistré dans la base de données.
     * @throws AdministrateurException Si les informations de l'administrateur ne sont pas valides (par exemple, si des champs obligatoires sont manquants ou incorrects), une exception est lancée.
     */
    @Override
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        verifierAdmin(administrateurRequestDTO);
        Administrateur administrateur = administrateurMapper.toAdministrateur(administrateurRequestDTO);
        Administrateur administrateurRetour = administrateurDAO.save(administrateur);
        return administrateurMapper.toAdministrateurResponseDTO(administrateurRetour);
    }

    /**
     * Récupère la liste de tous les administrateurs et les convertit en une liste de {@link AdministrateurResponseDTO}.
     *
     * Cette méthode interroge la base de données pour récupérer tous les administrateurs à l'aide de la méthode administrateurDAO.findAll.
     * Ensuite, elle transforme chaque objet {@link Administrateur} en un objet {@link AdministrateurResponseDTO} à l'aide du administrateurMapper.
     * Enfin, elle retourne la liste des administrateurs sous forme de {@link AdministrateurResponseDTO}.
     *
     * @return Une liste d'objets {@link AdministrateurResponseDTO} représentant tous les administrateurs présents dans la base de données.
     */
    @Override
    public List<AdministrateurResponseDTO> listerAdmin() {
        List<Administrateur> listeA = administrateurDAO.findAll();
        return listeA.stream()
                .map(administrateurMapper::toAdministrateurResponseDTO)
                .toList();
    }

    /**
     * Recherche un administrateur par son email et mot de passe et retourne sa représentation sous forme de {@link AdministrateurResponseDTO}.
     *
     * Cette méthode vérifie d'abord que l'email et le mot de passe sont fournis et valides. Ensuite, elle cherche un administrateur
     * dans la base de données en fonction de l'email et du mot de passe fournis. Si un administrateur est trouvé, il est converti en
     * un objet {@link AdministrateurResponseDTO} et retourné. Si aucun administrateur correspondant n'est trouvé ou si les
     * paramètres requis sont manquants, une exception {@link AdministrateurException} est lancée.
     *
     * @param email L'email de l'administrateur à rechercher. Ne peut pas être null ou vide.
     * @param password Le mot de passe de l'administrateur à rechercher. Ne peut pas être null ou vide.
     * @return Un objet {@link AdministrateurResponseDTO} représentant l'administrateur trouvé.
     * @throws AdministrateurException Si les paramètres sont manquants (email ou mot de passe), ou si aucun administrateur n'est trouvé
     *         avec les paramètres fournis, une exception est lancée.
     */
    @Override
    public AdministrateurResponseDTO trouverAdminParEmailEtPassword(String email, String password) {
        // Si Optadministrateur vide > Exception sinon renvoyer le Mapper du administrateur.
        if (email == null || email.isBlank())
            throw new AdministrateurException("L'email est obligatoire");
        if (password == null || password.isBlank())
            throw new AdministrateurException("Le mot de passe est obligatoire");
        return administrateurMapper.toAdministrateurResponseDTO(
                administrateurDAO
                        .findByEmailAndPassword(email, password)
                        .orElseThrow(() -> new AdministrateurException("Il faut les paramètre requis pour proceder à la recherche"))
        );
    }

    /**
     * Supprime un administrateur de la base de données en fonction de son email et mot de passe.
     *
     * Cette méthode vérifie d'abord si un administrateur correspondant à l'email et au mot de passe fournis existe dans la base de données.
     * Si aucun administrateur n'est trouvé, une exception {@link AdministrateurException} est lancée. Ensuite, la méthode vérifie qu'il reste
     * au moins un administrateur dans la base de données. Si l'administrateur à supprimer est le dernier dans le système, une exception est également lancée.
     * Si ces conditions sont remplies, l'administrateur est supprimé de la base de données.
     *
     * @param email L'email de l'administrateur à supprimer. Ne peut pas être null ou vide.
     * @param password Le mot de passe de l'administrateur à supprimer. Ne peut pas être null ou vide.
     * @throws AdministrateurException Si aucun administrateur correspondant aux paramètres fournis n'est trouvé, ou si la suppression
     *         entraînerait qu'il n'y ait plus d'administrateurs dans le système, une exception est lancée.
     */
    @Override
    public void supprimer(String email, String password) throws AdministrateurException {
        Administrateur admin = administrateurDAO.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new AdministrateurException("Le compte que vous chercher a supprimer n'existe pas..."));
        if(administrateurDAO.count() ==1)
            throw new AdministrateurException("Il faut au moins un admin !");
        administrateurDAO.delete(admin);
    }

    /**
     * Modifie les informations d'un administrateur existant dans la base de données.
     *
     * Cette méthode commence par vérifier qu'un administrateur avec l'email et le mot de passe fournis existe dans la base de données.
     * Si l'administrateur est trouvé, ses informations sont mises à jour en utilisant les données fournies dans {@link AdministrateurRequestDTO}.
     * Les informations de l'administrateur existant sont remplacées par les nouvelles informations, puis l'administrateur modifié est enregistré dans la base de données.
     * Enfin, un objet {@link AdministrateurResponseDTO} représentant l'administrateur mis à jour est retourné.
     *
     * @param email L'email de l'administrateur à modifier. Ne peut pas être null ou vide.
     * @param password Le mot de passe de l'administrateur à modifier. Ne peut pas être null ou vide.
     * @param administrateurRequestDTO L'objet contenant les nouvelles informations de l'administrateur. Ne peut pas être null.
     * @return Un objet {@link AdministrateurResponseDTO} représentant l'administrateur modifié, après enregistrement dans la base de données.
     * @throws AdministrateurException Si aucun administrateur correspondant à l'email et au mot de passe fournis n'est trouvé, ou si une erreur se produit lors de la modification.
     */
    @Override
    public AdministrateurResponseDTO modifier(String email, String password, AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        Optional<Administrateur> optAdministrateur = administrateurDAO.findByEmailAndPassword(email,password);
        if(optAdministrateur.isEmpty()) throw new AdministrateurException("Le compte que vous chercher n'existe pas");
        Administrateur administrateur = administrateurMapper.toAdministrateur(administrateurRequestDTO);
        Administrateur administrateurExistant = optAdministrateur.get();
        remplacer(administrateur, administrateurExistant);
        Administrateur administrateurEnreg = administrateurDAO.save(administrateurExistant);
        return administrateurMapper.toAdministrateurResponseDTO(administrateurEnreg);
    }

    /**
     * Remplace les informations d'un administrateur existant par celles d'un nouveau administrateur.
     *
     * Cette méthode met à jour les propriétés de l'administrateur existant avec les valeurs correspondantes provenant du nouveau administrateur,
     * si ces valeurs ne sont pas nulles. Seules les propriétés non nulles du nouveau administrateur sont copiées dans l'administrateur existant.
     *
     * @param nouveauAdministrateur L'administrateur contenant les nouvelles informations. Ne peut pas être null.
     * @param administrateurExistante L'administrateur à mettre à jour. Ne peut pas être null.
     */

    private static void remplacer(Administrateur nouveauAdministrateur, Administrateur administrateurExistante) {
        if(nouveauAdministrateur.getEmail()!=null)
            administrateurExistante.setEmail(nouveauAdministrateur.getEmail());
        if(nouveauAdministrateur.getPassword()!=null)
            administrateurExistante.setPassword(nouveauAdministrateur.getPassword());
        if (nouveauAdministrateur.getNom() !=null)
            administrateurExistante.setNom(nouveauAdministrateur.getNom());
        if (nouveauAdministrateur.getPrenom() !=null)
            administrateurExistante.setPrenom(nouveauAdministrateur.getPrenom());
        if (nouveauAdministrateur.getFonction() !=null)
            administrateurExistante.setFonction(nouveauAdministrateur.getFonction());
    }

    /**
     * Vérifie la validité des informations d'un administrateur avant de procéder à son enregistrement.
     *
     * Cette méthode valide les données d'un administrateur fournies dans un objet {@link AdministrateurRequestDTO}.
     * Elle vérifie que les champs obligatoires (nom, prénom, mot de passe, email, fonction) sont remplis et que le mot de passe respecte les critères de sécurité spécifiés.
     * Si l'un des champs est invalide ou vide, une exception {@link AdministrateurException} est lancée avec un message approprié.
     *
     * @param administrateurRequestDTO L'objet contenant les informations de l'administrateur à valider. Ne peut pas être null.
     * @throws AdministrateurException Si l'un des champs de l'administrateur est invalide (null ou vide) ou si le mot de passe ne respecte pas les critères de sécurité.
     */
    private  void verifierAdmin(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        if (administrateurRequestDTO == null)
            throw new AdministrateurException("L'administrateur ne peux pas être null");
        if (administrateurRequestDTO.nom() == null || administrateurRequestDTO.nom().isBlank())
            throw new AdministrateurException("Le nom ne peu pas être null");
        if (administrateurRequestDTO.prenom() == null || administrateurRequestDTO.prenom().isBlank())
            throw new AdministrateurException("Le prenom ne peu pas être null");
        if (administrateurRequestDTO.password() == null || administrateurRequestDTO.password().isBlank())
            throw new AdministrateurException("Le mot de passe est non défini");
        if (administrateurRequestDTO.email() == null || administrateurRequestDTO.email().isBlank())
            throw new AdministrateurException("l'email est vide");
        if (!administrateurRequestDTO.password().matches(REGEX_MDP))
            throw new AdministrateurException("Le mot de passe doit contenir au moins 8 charactère et maximum 16, il doit également avoir une majuscule, une minuscule, un chiffre et un des carractères spéciaux & # @ - _ § au moins.");
        if (administrateurRequestDTO.fonction() == null || administrateurRequestDTO.fonction().isBlank())
            throw new AdministrateurException("La fonction dois être définie");

    }
}
