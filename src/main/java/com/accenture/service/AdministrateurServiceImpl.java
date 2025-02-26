package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
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
     * Méthode Ajouter(AdministrateurRequestDTO AdministrateurRequestDTO)
     *
     * @param administrateurRequestDTO L'objet métier Administrateur en base qui contient tous les attribus de Adminitrateur
     * @return retourne AdministrateurResponseDTO
     * @throws AdministrateurException Si l'administrateur ne suis pas les contraintes établies pour l'enregistrement d'un nouvel Admin.
     */
    @Override
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        verifierAdmin(administrateurRequestDTO);
        Administrateur administrateur = administrateurMapper.toAdministrateur(administrateurRequestDTO);
        System.out.println(administrateur);
        Administrateur administrateurRetour = administrateurDAO.save(administrateur);
        return administrateurMapper.toAdministrateurResponseDTO(administrateurRetour);
    }

    /**
     * Méthode listeAdmin()
     *
     * @return La méthode nous retourne la liste de tous les administrateurs en base par l'intermédiaire d'un stream.
     */
    @Override
    public List<AdministrateurResponseDTO> listeAdmin() {
        List<Administrateur> listeA = administrateurDAO.findAll();
        return listeA.stream()
                .map(administrateurMapper::toAdministrateurResponseDTO)
                .toList();
    }


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

    @Override
    public void supprimer(String email, String password) throws AdministrateurException {
        Administrateur admin = administrateurDAO.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new AdministrateurException("Le compte que vous chercher a supprimer n'existe pas..."));
        administrateurDAO.delete(admin);
    }


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
     * verifierAdmin(AdministrateurRequestDTO administrateurRequestDTO)
     *
     * @param administrateurRequestDTO Nous permet d'aller chercher les paramètres d'admin. pour la vérification
     * @throws AdministrateurException lorsqu'un paramètre de AdministrateurrequestDTO est null iy blank, il renvoie l'exception correspondante au paramètre.
     */
    private static void verifierAdmin(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
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
