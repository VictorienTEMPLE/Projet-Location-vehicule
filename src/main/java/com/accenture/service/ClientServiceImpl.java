package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService{
    public static final String ID_NON_PRESENT = "id non present";
    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;
    private static final String REGEX_MDP ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    /**
     * Méthode Ajouter(ClientRequestDTO clientRequestDTO)
     * @param clientRequestDTO L'objet métier Client en base qui contient tous les attribus de Client
     * @return  retourne ClientResponseDTO
     * @throws ClientException Si le client ne suis pas les contraintes établies pour l'inscription
     */
    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException{
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);
//        client.setDateDInscription(LocalDate.now());
        client.setDesactive(false);
        System.out.println(client);
        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);}

    @Override
    public ClientResponseDTO trouver(String email) throws ClientException {
        return null;
    }

    @Override
    public ClientResponseDTO modifier(String email, ClientRequestDTO clientRequestDTO) throws ClientException {
        if (!clientDAO.existsById(email)) throw new ClientException("ID non trouvée");
        Client client = clientMapper.toClient(clientRequestDTO);


        Client clientEnreg = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientEnreg);    }

    @Override
    public void supprimer(String email) throws ClientException {
        if (clientDAO.existsById(email))
            clientDAO.deleteById(email);
        else
            throw new ClientException("L'email ne correspond à aucun compte !");
    }
    @Override
   public List<ClientResponseDTO> liste(){
       List<Client> listeC = clientDAO.findAll();
       return listeC.stream()
               .map(clientMapper::toClientResponseDTO)
               .toList();
   }

   @Override
   public ClientResponseDTO trouverUserParEmailEtPassword(String email, String password){
       Optional<Client> optClient = clientDAO.findByEmailAndPassword(email,password);
       // Si Optclient vide > Exception sinon renvoyer le Mapper du client.
       if (optClient.isEmpty())
           throw new ClientException("Il faut les paramètre requis pour proceder à la recherche");
       else
           return clientMapper.toClientResponseDTO(optClient.get());
   }



    private static void verifierClient(ClientRequestDTO clientRequestDTO) throws ClientException{
        if (clientRequestDTO == null)
            throw new ClientException("Le client ne peux pas être null");
        if (clientRequestDTO.adresse() ==null)
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
        if (clientRequestDTO.dateDeNaissance()== null)
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