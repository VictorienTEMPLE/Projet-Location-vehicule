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
     * Ajoute un nouveau client à la base de données.
     *
     * Cette méthode vérifie d'abord la validité des informations du client à l'aide de la méthode verifierClient.
     * Ensuite, elle mappe les informations de l'objet {@link ClientRequestDTO} vers un objet {@link Client}.
     * Le client est marqué comme actif (en définissant le champ `desactive` à `false`), puis enregistré dans la base de données.
     * Enfin, un objet {@link ClientResponseDTO} représentant le client ajouté est retourné.
     *
     * @param clientRequestDTO L'objet contenant les informations du client à ajouter. Ne peut pas être null.
     * @return Un objet {@link ClientResponseDTO} représentant le client ajouté, une fois enregistré dans la base de données.
     * @throws ClientException Si les informations du client ne sont pas valides (par exemple, si des champs obligatoires sont manquants), une exception est lancée.
     */
    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException {
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);
        client.setDesactive(false);
        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);
    }

    /**
     * Recherche un client par son email et retourne sa représentation sous forme de {@link ClientResponseDTO}.
     *
     * Cette méthode cherche un client dans la base de données en fonction de l'email fourni. Si le client est trouvé,
     * il est converti en un objet {@link ClientResponseDTO} et retourné. Si aucun client correspondant n'est trouvé,
     * une exception {@link ClientException} est lancée.
     *
     * @param email L'email du client à rechercher. Ne peut pas être null.
     * @return Un objet {@link ClientResponseDTO} représentant le client trouvé.
     * @throws ClientException Si aucun client n'est trouvé avec l'email donné, une exception est lancée avec un message approprié.
     */
    @Override
    public ClientResponseDTO trouver(String email) throws ClientException {
        Optional<Client> optClient = clientDAO.findById(email);
        if(optClient.isEmpty()) throw new ClientException("Id non trouvée");
        Client client = optClient.get();
        return clientMapper.toClientResponseDTO(client);
    }

    /**
     * Modifie les informations d'un client en fonction de son email, mot de passe et des nouvelles données fournies.
     *
     * Cette méthode recherche un client existant dans la base de données en fonction de l'email et du mot de passe fournis.
     * Si aucun client n'est trouvé, une exception {@link ClientException} est lancée.
     * Ensuite, elle mappe les informations de `clientRequestDTO` dans un objet {@link Client},
     * et met à jour les données de l'objet client existant. Les modifications sont ensuite enregistrées dans la base de données.
     * Enfin, un objet {@link ClientResponseDTO} représentant le client mis à jour est retourné.
     *
     * @param email L'email du client à modifier. Ne peut pas être null ou vide.
     * @param password Le mot de passe du client à modifier. Ne peut pas être null ou vide.
     * @param clientRequestDTO L'objet contenant les nouvelles informations du client à mettre à jour.
     * @return Un objet {@link ClientResponseDTO} représentant le client mis à jour.
     * @throws ClientException Si aucun client n'est trouvé avec les paramètres fournis, une exception est lancée.
     */
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

    /**
     * Remplace les informations d'un client existant avec celles d'un nouveau client.
     *
     * Cette méthode met à jour l'objet `clientExistante` avec les valeurs présentes dans `nouveauclient`.
     * Seules les valeurs non nulles ou valides sont prises en compte pour la mise à jour.
     * Les informations suivantes sont mises à jour :
     * <ul>
     *     <li>Email</li>
     *     <li>Mot de passe</li>
     *     <li>Adresse (code postal, rue, numéro)</li>
     *     <li>Date d'inscription (si la date de naissance est renseignée)</li>
     *     <li>Liste des permis</li>
     *     <li>Nom et prénom</li>
     * </ul>
     * Si une valeur correspondante dans `nouveauclient` est non nulle, elle remplace la valeur correspondante dans `clientExistante`.
     *
     * @param nouveauclient L'objet contenant les nouvelles informations à appliquer au client existant.
     * @param clientExistante L'objet client existant qui sera mis à jour avec les informations de `nouveauclient`.
     */

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
     * Supprime un client en fonction de son email et de son mot de passe.
     *
     * Cette méthode recherche un client dans la base de données à l'aide de l'email et du mot de passe fournis.
     * Si aucun client correspondant n'est trouvé, une exception {@link ClientException} est lancée.
     * Si le client est trouvé, il est supprimé de la base de données via clientDAO.
     *
     * @param email L'email du client à supprimer. Ne peut pas être null ou vide.
     * @param password Le mot de passe du client à supprimer. Ne peut pas être null ou vide.
     * @throws ClientException Si aucun client n'est trouvé avec les paramètres fournis, une exception est lancée.
     */
    @Override
    public void supprimer(String email, String password) throws ClientException {
        Client client = clientDAO.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ClientException("Le compte que vous chercher a supprimer n'existe pas..."));
        clientDAO.delete(client);
    }

    /**
     * Récupère la liste de tous les clients et les convertit en objets {@link ClientResponseDTO}.
     *
     * Cette méthode utilise clientDAO pour récupérer tous les clients présents dans la base de données.
     * Ensuite, chaque objet {@link Client} est converti en un objet {@link ClientResponseDTO} à l'aide de clientMapper.
     * La liste des {@link ClientResponseDTO} est ensuite renvoyée.
     *
     * @return Une liste de {@link ClientResponseDTO} représentant tous les clients dans la base de données.
     */
    @Override
    public List<ClientResponseDTO> lister() {
        List<Client> listeC = clientDAO.findAll();
        return listeC.stream()
                .map(clientMapper::toClientResponseDTO)
                .toList();
    }

    /**
     * Recherche un client en fonction de son email et de son mot de passe.
     *
     * Cette méthode vérifie d'abord si l'email et le mot de passe sont fournis. Si l'un d'eux est absent ou vide, une exception {@link ClientException} est lancée.
     * Ensuite, elle effectue une recherche dans la base de données via le  clientDAO pour trouver un client correspondant à l'email et au mot de passe fournis.
     * Si aucun client n'est trouvé, une exception {@link ClientException} est lancée.
     * Si un client est trouvé, il est converti en {@link ClientResponseDTO} à l'aide du clientMapper et retourné.
     *
     * @param email L'email du client à rechercher. Ne peut pas être null ou vide.
     * @param password Le mot de passe du client à rechercher. Ne peut pas être null ou vide.
     * @return Un objet {@link ClientResponseDTO} représentant le client trouvé.
     * @throws ClientException Si l'email ou le mot de passe est manquant, ou si aucun client n'est trouvé pour les paramètres fournis.
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