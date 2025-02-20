package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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

    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) {
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);
//        client.setDateDInscription(LocalDate.now());
        client.setDesactive(false);
        System.out.println(client);
        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);}

    @Override
    public ClientResponseDTO trouver(int id) throws ClientException {
        return null;
    }

    @Override
    public ClientResponseDTO modifier(int id, ClientRequestDTO clientRequestDTO) throws ClientException {
        if (!clientDAO.existsById(id)) throw new ClientException("ID non trouvée");
        Client client = clientMapper.toClient(clientRequestDTO);
        client.setId(id);

        Client clientEnreg = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientEnreg);    }

    @Override
    public void supprimer(int id) throws ClientException {
        if (clientDAO.existsById(id))
            clientDAO.deleteById(id);
        else
            throw new ClientException("L'id ne correspond a rien !");
    }


    private static void verifierClient(ClientRequestDTO clientRequestDTO) throws ClientException{
        if (clientRequestDTO.adresse().getNumero() == 0)
            throw new ClientException("Le numéro de l'adresse est invalide");
        if (clientRequestDTO.adresse().getRue() == null || clientRequestDTO.adresse().getRue().isBlank())
            throw new ClientException("La rue est invalide ou vide");
        if (clientRequestDTO.adresse().getCodePostal() == null || clientRequestDTO.adresse().getCodePostal().isBlank())
            throw new ClientException("le code postal est null ou vide");
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