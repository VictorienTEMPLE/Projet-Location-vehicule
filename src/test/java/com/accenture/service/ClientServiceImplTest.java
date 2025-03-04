package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import com.accenture.shared.Permis;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock //on crée le mockito.mock ici
    ClientDAO daoMock = Mockito.mock(ClientDAO.class);
    @Mock
    ClientMapper mapperMock;
    @InjectMocks //on créer le new tachServiceImpl ici
    ClientServiceImpl service;
     Permis permis= Permis.B;

    /* *********************************************** *
     *                                                 *
     *                 methodes privées                *
     *                                                 *
     * *********************************************** *
     */


    private  ClientRequestDTO clientRequestDTOUn(){

        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(0, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
    return dto;
    }

    private static Client clientProfilPourTests() {
        Client c = new Client();
        c.setAdresse(new Adresse(1, 3, "Rue JoeLeptit", "44800", "Saint-Herblain"));
        c.setNom("Legrand");
        c.setPrenom("Joe");
        c.setDateDeNaissance(LocalDate.of(2004, 8, 12));
        c.setDateInscription(LocalDate.of(2025, 8, 12));
        c.setListePermis(Permis.B);
        c.setDesactive(false);
        c.setPassword("P@ssword2");
        c.setEmail("ceciestuneemail@email.com");
        return c;
    }

    private static ClientResponseDTO creerClientResponseDTOExample() {
        HashSet<Permis> permis1 = new HashSet<>();
        permis1.add(Permis.B);
        return new ClientResponseDTO(new AdresseDTO(3, "Rue JoeLeptit", "44800", "Saint-Herblain"), "Legrand", "Jack", LocalDate.of(2004, 8, 12), LocalDate.of(2025, 8, 12), Permis.B, false, "ceciestuneemail@email.com");
    }


    private static ClientResponseDTO creerClientResponseDTOExample2() {
        HashSet<Permis> permis2 = new HashSet<>();
        permis2.add(Permis.B);
        return new ClientResponseDTO(new AdresseDTO(12, "Rue JoeLeptit", "44800", "Saint-Herblain"), "Legrand", "Joe", LocalDate.of(2004, 8, 12), LocalDate.of(2025, 8, 12), Permis.B, false, "emailfun@email.com");
    }

    private static ClientResponseDTO clientResponseProfilPourTests2() {
        ClientResponseDTO a = new ClientResponseDTO(new AdresseDTO(12, "Rue JoeLeptit", "44800", "Saint-Herblain"), "Joestar", "Johnathan", LocalDate.of(2000, 11, 24), LocalDate.now(), Permis.B, false, "sympa@email.com");
        return a;
    }


    /* *********************************************** *
     *                                                 *
     *                methodes de tests                *
     *                                                 *
     * *********************************************** *
     */

    @Test
    void testAjouterNull() {
        assertThrows(ClientException.class, () -> service.ajouter(null));
    }

    @Test
    void testAjouterSansAdresse() {
        ClientRequestDTO dto = new ClientRequestDTO(null, "Joe", "Legrand", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansNumeroAdresse() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(0, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansRueAdresse() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(1, null, "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansCodePostalAdresse() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(1, "Rue joe legrand", null, "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansVilleAdresse() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(1, "Rue joe legrand", "44000", null), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansNom() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), null, "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansPrenom() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", null, LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansDateNaissance() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", null, LocalDate.now(), permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansDateInscription() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), null, permis, false, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansDesactive() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, null, "P@ssword1", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansPassword() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, null, "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterPasswordMauvais() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "pswordmauvais", "blablabla@mail.com");
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterSansEmail() {
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", null);
        assertThrows(ClientException.class, () -> service.ajouter(dto));
    }

    @Test
    void testAjouterOk() {
        ClientRequestDTO requestDto = new ClientRequestDTO(new AdresseDTO(12, "Rue JoeLeptit", "44800", "Saint-Herblain"), "Legrand", "Jack", LocalDate.of(2004, 8, 12), LocalDate.of(2025, 8, 12), permis, false, "P@ssword3", "emailfun@email.com");
        Client tacheAvantEnreg = clientProfilPourTests();

        Client tacheApresEnreg = clientProfilPourTests();
        ClientResponseDTO responseDto = creerClientResponseDTOExample();

        Mockito.when(mapperMock.toClient(requestDto)).thenReturn((tacheAvantEnreg));
        Mockito.when(daoMock.save(tacheAvantEnreg)).thenReturn(tacheApresEnreg);
        Mockito.when(mapperMock.toClientResponseDTO(tacheApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouter(requestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(tacheAvantEnreg);
    }



    @Test
    void listeclient() {
        Client client1 = clientProfilPourTests();
        Client client2 = new Client();
        client2.setAdresse(new Adresse(1, 12, "Rue JoeLeptit", "44800", "Saint-Herblain"));
        client2.setNom("Legrand");
        client2.setPrenom("Joe");
        client2.setDateDeNaissance(LocalDate.of(2004, 8, 12));
        client2.setDateInscription(LocalDate.of(2025, 8, 12));
        client2.setListePermis(Permis.B);
        client2.setDesactive(false);
        client2.setPassword("P@ssword2");
        client2.setEmail("ceciestuneemail@email.com");


        List<Client> client = List.of(client1, client2);

        List<ClientResponseDTO> dtolist = List.of(creerClientResponseDTOExample(), creerClientResponseDTOExample2());

        Mockito.when(daoMock.findAll()).thenReturn(client);
        Mockito.when(mapperMock.toClientResponseDTO(client1)).thenReturn(creerClientResponseDTOExample());
        Mockito.when(mapperMock.toClientResponseDTO(client2)).thenReturn(creerClientResponseDTOExample2());

        assertEquals(dtolist, service.lister());
    }

    @Test
    void testTrouverClientParEmailEtPasswordSansEmail() {
        assertThrows(ClientException.class, () -> service.trouverClientParEmailEtPassword(null, "P@ssword1"));
    }

    @Test
    void testTrouverClientParEmailEtPasswordSansPassword() {
        assertThrows(ClientException.class, () -> service.trouverClientParEmailEtPassword("emailsympa@email.com", null));
    }

    @Test
    void testTrouverClientParEmailEtPasswordMauvaisPassword() {
        assertThrows(ClientException.class, () -> service.trouverClientParEmailEtPassword("emailsympa@email.com", "P@ss"));
    }

    @Test
    void testTrouverClientParEmailEtPasswordOk() {
        Client client1 = clientProfilPourTests();
        ClientResponseDTO clientResponseDTO = clientResponseProfilPourTests2();
        Mockito.when(daoMock.findByEmailAndPassword("sympa@email.com", "P@ssword1")).thenReturn(Optional.of(client1));
        Mockito.when(mapperMock.toClientResponseDTO(client1)).thenReturn(clientResponseDTO);
        assertEquals(clientResponseDTO, service.trouverClientParEmailEtPassword("sympa@email.com", "P@ssword1"));
        Mockito.verify(daoMock, Mockito.times(1)).findByEmailAndPassword("sympa@email.com", "P@ssword1");
    }



    @Test
    void testSupprimerSansEmail(){
        assertThrows(ClientException.class, () -> service.supprimer(null, "P@ssword1"));
    }

    @Test
    void testSupprimerSansMotDePasse(){
        assertThrows(ClientException.class, () -> service.supprimer("email1@email.com", "null"));
    }

    @Test
    void testSupprimerOk(){
        Client client1 = clientProfilPourTests();
        Mockito.when(daoMock.findByEmailAndPassword(client1.getEmail(),client1.getPassword())).thenReturn(Optional.of(client1));
        service.supprimer(client1.getEmail(),client1.getPassword());
        Mockito.verify(daoMock, Mockito.times(1)).delete(client1);

    }

    private static Client clientProfilPourTests2() {
        Client c = new Client();
        c.setAdresse(new Adresse(1, 15, "Rue JoeLeptit", "44800", "Saint-Herblain"));
        c.setNom("Legrand");
        c.setPrenom("Joel");
        c.setDateDeNaissance(LocalDate.of(2002, 8, 12));
        c.setDateInscription(LocalDate.of(2025, 8, 12));
        c.setListePermis(Permis.B);
        c.setDesactive(false);
        c.setPassword("P@ssword3");
        c.setEmail("ceciestuneemail@email.com");
        return c;
    }


    @Test
    void testModifierSansEmail(){
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "sympa@email.com");
        assertThrows(ClientException.class, ()-> service.modifier(null, "P@ssword1", dto ));
    }

    @Test
    void testModifierSansPassword(){
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "sympa@email.com");
        assertThrows(ClientException.class, ()-> service.modifier("sympa@email.com", null, dto ));
    }

    @Test
    void testModifierSansRequestDTO(){
        ClientRequestDTO dto = new ClientRequestDTO(new AdresseDTO(2, "Rue joe legrand", "44000", "Nantes"), "Legrand", "Joe", LocalDate.now(), LocalDate.now(), permis, false, "P@ssword1", "sympa@email.com");
        assertThrows(ClientException.class, ()-> service.modifier("sympa@email.com", "P@ssword1", null ));
    }

    @Test
    void testModifierOk(){
        Client client =  clientProfilPourTests();
        Mockito.when(mapperMock.toClient(clientRequestDTOUn())).thenReturn(client);
        Mockito.when(daoMock.findByEmailAndPassword(client.getEmail(),client.getPassword())).thenReturn(Optional.of(client));
        service.modifier(client.getEmail(),client.getPassword(),clientRequestDTOUn());
    }

}

