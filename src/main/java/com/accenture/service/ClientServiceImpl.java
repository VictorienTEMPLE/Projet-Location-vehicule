package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;
    private static final String REGEX_MDP = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    /**
     * Méthode Ajouter(ClientRequestDTO clientRequestDTO)
     *
     * @param clientRequestDTO L'objet métier Client en base qui contient tous les attribus de Client
     * @return retourne ClientResponseDTO
     * @throws ClientException Si le client ne suis pas les contraintes établies pour l'inscription
     */
    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException {
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);
//        client.setDateDInscription(LocalDate.now());
        client.setDesactive(false);
        System.out.println(client);
        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);
    }

    @Override
    public ClientResponseDTO trouver(String email) throws ClientException {
        Optional<Client> optClient = clientDAO.findById(email);
        if(optClient.isEmpty()) throw new ClientException("Id non trouvée");
        Client client = optClient.get();
        return clientMapper.toClientResponseDTO(client);
    }

    @Override
    public ClientResponseDTO modifier(String email, String password, ClientRequestDTO clientRequestDTO) throws ClientException {
        Optional<Client> optClient = clientDAO.findByEmailAndPassword(email,password);
        if(optClient.isEmpty()) throw new ClientException("Le compte que vous chercher n'existe pas");
        Client client = clientMapper.toClient(clientRequestDTO);
        Client clientExistant = optClient.get();
        remplacer(client, clientExistant);
        Client clientEnreg = clientDAO.save(clientExistant);
        return clientMapper.toClientResponseDTO(clientEnreg);
    }

    private static void remplacer(Client nouveauclient, Client clientExistante) {
        if(nouveauclient.getEmail()!=null)
            clientExistante.setEmail(nouveauclient.getEmail());
        if(nouveauclient.getPassword()!=null)
            clientExistante.setPassword(nouveauclient.getPassword());
        if (nouveauclient.getAdresse().getCodePostal() !=null)
            clientExistante.getAdresse().setCodePostal(nouveauclient.getAdresse().getCodePostal());
        if (nouveauclient.getAdresse().getRue() !=null)
            clientExistante.getAdresse().setRue(nouveauclient.getAdresse().getRue());
        if (nouveauclient.getAdresse().getNumero()!=0)
            clientExistante.getAdresse().setNumero(nouveauclient.getAdresse().getNumero());
        if (nouveauclient.getDateDeNaissance()!=null)
            clientExistante.setDateInscription(nouveauclient.getDateInscription());
        if (nouveauclient.getListePermis() !=null)
            clientExistante.setListePermis(nouveauclient.getListePermis());
        if (nouveauclient.getNom() !=null)
            clientExistante.setNom(nouveauclient.getNom());
        if (nouveauclient.getPrenom() !=null)
            clientExistante.setPrenom(nouveauclient.getPrenom());
    }
    /**
     * Méthode supprimer(String email, String password)
     * @param email Requiert un string email pour trouver l'objet correspondant en base
     * @param password Requiert un string password pour trouver l'objet correspondant en base
     * @throws ClientException renvoie une clientException si les contraintes ne sont pas suivies
     */
    @Override
    public void supprimer(String email, String password) throws ClientException {
        Client client = clientDAO.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ClientException("Le compte que vous chercher a supprimer n'existe pas..."));
        clientDAO.delete(client);
    }

    /**
     * méthode Liste()
     * @return retourne la liste complète de tous les objet Client en base
     */
    @Override
    public List<ClientResponseDTO> liste() {
        List<Client> listeC = clientDAO.findAll();
        return listeC.stream()
                .map(clientMapper::toClientResponseDTO)
                .toList();
    }

    /**
     * Méthode trouverClientParEmailEtPassword(String email, String password)
     * @param email requiert un paramètre String email
     * @param password requiert un paramètre String password
     * @return retourne l'objet client correspondant aux String email et String password donné.
     */

    @Override
    public ClientResponseDTO trouverClientParEmailEtPassword(String email, String password) {
        // Si Optclient vide > Exception sinon renvoyer le Mapper du client.
        if (email == null || email.isBlank())
            throw new ClientException("L'email est obligatoire");
        if (password == null || password.isBlank())
            throw new ClientException("Le mot de passe est obligatoire");
        return clientMapper.toClientResponseDTO(
                clientDAO
                        .findByEmailAndPassword(email, password)
                        .orElseThrow(() -> new ClientException("Il faut les paramètre requis pour proceder à la recherche"))
        );
    }


    private static void verifierClient(ClientRequestDTO clientRequestDTO) throws ClientException {
        if (clientRequestDTO == null)
            throw new ClientException("Le client ne peux pas être null");
        if (clientRequestDTO.adresse() == null)
            throw new ClientException("L'adresse est obligatoire");
        if (clientRequestDTO.adresse().numero() == 0)
            throw new ClientException("Le numéro de l'adresse est invalide");
        if (clientRequestDTO.adresse().rue() == null || clientRequestDTO.adresse().rue().isBlank())
            throw new ClientException("La rue est invalide ou vide");
        if (clientRequestDTO.adresse().codePostal() == null || clientRequestDTO.adresse().codePostal().isBlank())
            throw new ClientException("le code postal est null ou vide");
        if (clientRequestDTO.adresse().ville() == null || clientRequestDTO.adresse().ville().isBlank())
            throw new ClientException("La ville ne peu pas être vide");
        if (clientRequestDTO.nom() == null || clientRequestDTO.nom().isBlank())
            throw new ClientException("Le nom ne peu pas être null");
        if (clientRequestDTO.prenom() == null || clientRequestDTO.prenom().isBlank())
            throw new ClientException("Le prenom ne peu pas être null");
        if (clientRequestDTO.dateDeNaissance() == null)
            throw new ClientException("La date de naissance est vide");
        if (clientRequestDTO.dateInscription() == null)
            throw new ClientException("La date d'inscription est vide");
        if (clientRequestDTO.desactive() == null)
            throw new ClientException("Il faut savoir si votre compte est activé ou désactivé");
        if (clientRequestDTO.password() == null || clientRequestDTO.password().isBlank())
            throw new ClientException("Le mot de passe est non défini");
        if (clientRequestDTO.email() == null || clientRequestDTO.email().isBlank())
            throw new ClientException("l'email est vide");
        if (!clientRequestDTO.password().matches(REGEX_MDP))
            throw new ClientException("Le mot de passe doit contenir au moins 8 charactère et maximum 16, il doit également avoir une majuscule, une minuscule, un chiffre et un des carractères spéciaux & # @ - _ § au moins.");
    }
}