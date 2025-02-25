package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdministrateurServiceImpl implements AdministrateurService{
    private final AdministrateurDAO administrateurDAO;
    private final AdministrateurMapper administrateurMapper;
    private static final String REGEX_MDP ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public AdministrateurServiceImpl(AdministrateurDAO administrateurDAO, AdministrateurMapper administrateurMapper) {
        this.administrateurDAO = administrateurDAO;
        this.administrateurMapper = administrateurMapper;
    }

    /**
     * Méthode Ajouter(AdministrateurRequestDTO AdministrateurRequestDTO)
     * @param administrateurRequestDTO L'objet métier Client en base qui contient tous les attribus de Adminitrateur
     * @return  retourne AdministrateurResponseDTO
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
     * @return La méthode nous retourne la liste de tous les administrateurs en base par l'intermédiaire d'un stream.
     */
    @Override
    public List<AdministrateurResponseDTO> listeAdmin(){
        List<Administrateur> listeA = administrateurDAO.findAll();
        return listeA.stream()
                .map(administrateurMapper::toAdministrateurResponseDTO)
                .toList();
    }


    @Override
    public AdministrateurResponseDTO trouverAdminParEmailEtPassword(String email, String password) {
        Optional<Administrateur> optAdmin = administrateurDAO.findByEmailAndPassword(email, password);
        // Si Optclient vide > Exception sinon renvoyer le Mapper du client.
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
     * verifierAdmin(AdministrateurRequestDTO administrateurRequestDTO)
     * @param administrateurRequestDTO Nous permet d'aller chercher les paramètres d'admin. pour la vérification
     * @throws AdministrateurException lorsqu'un paramètre de AdministrateurrequestDTO est null iy blank, il renvoie l'exception correspondante au paramètre.
     */
    private static void verifierAdmin(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException{
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
        if (administrateurRequestDTO.fonction() ==null || administrateurRequestDTO.fonction().isBlank())
            throw new AdministrateurException("La fonction dois être définie");
    }
}
