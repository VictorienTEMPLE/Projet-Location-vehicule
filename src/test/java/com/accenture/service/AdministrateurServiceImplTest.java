package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdministrateurServiceImplTest {

    @Mock //on crée le mockito.mock ici
    AdministrateurDAO daoMock = Mockito.mock(AdministrateurDAO.class);
    @Mock
    AdministrateurMapper mapperMock;
    @InjectMocks //on créer le new tachServiceImpl ici
    AdministrateurServiceImpl service;



    /* *********************************************** *
     *                                                 *
     *                methodes de tests                *
     *                                                 *
     * *********************************************** *
     */

    @Test
    void testAjouterNull() {
        assertThrows(AdministrateurException.class, ()-> service.ajouter(null));
    }

    @Test
    void testAjouterSansNom() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(null,"Joe","P@ssword1","blablabla@mail.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }
    @Test
    void testAjouterSansPrenom() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand",null,"P@ssword1","blablabla@mail.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }
    @Test
    void testAjouterSansPassword() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand","Joe",null,"blablabla@mail.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }
    @Test
    void testAjouterPasswordMauvais(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand","Joe","Word1","blablabla@mail.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }
    @Test
    void testAjouterSansEmail() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand","Joe","P@ssword1",null,"Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }

    @Test
    void testSansFonction(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand","Joe","P@ssword1","blabla@email.com",null);
        assertThrows(AdministrateurException.class, ()-> service.ajouter(dto));
    }

    @Test
    void testAjouterOk(){
        AdministrateurRequestDTO requestDto = new AdministrateurRequestDTO("Legrand","Joe","P@ssword1","blablabla@mail.com","Administrateur");
        Administrateur tacheAvantEnreg = adminProfilPourTests();

        Administrateur tacheApresEnreg = adminProfilPourTests();
        AdministrateurResponseDTO responseDto = creerAdminResponseDTOExample();

        Mockito.when(mapperMock.toAdministrateur(requestDto)).thenReturn((tacheAvantEnreg));
        Mockito.when(daoMock.save(tacheAvantEnreg)).thenReturn(tacheApresEnreg);
        Mockito.when(mapperMock.toAdministrateurResponseDTO(tacheApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto,service.ajouter(requestDto));
        Mockito.verify(daoMock,Mockito.times(1)).save(tacheAvantEnreg);
    }



    @Test
    void testlisteAdmin() {
        Administrateur admin1 = adminProfilPourTests();
        Administrateur admin2 = new Administrateur();
        admin2.setNom("Joestar");
        admin2.setPrenom("Johnathan");
        admin2.setPassword("P@ssword3");
        admin2.setEmail("blablabla@mail.com");
        admin2.setFonction("Administrateur2");


        List<Administrateur> admin = List.of(admin1,admin2);

        List<AdministrateurResponseDTO> dtolist = List.of(creerAdminResponseDTOExample(),creerAdminResponseDTOExample2());

        Mockito.when(daoMock.findAll()).thenReturn(admin);
        Mockito.when(mapperMock.toAdministrateurResponseDTO(admin1)).thenReturn(creerAdminResponseDTOExample());
        Mockito.when(mapperMock.toAdministrateurResponseDTO(admin2)).thenReturn(creerAdminResponseDTOExample2());

        assertEquals(dtolist,service.listerAdmin());
    }

    @Test
    void testTrouverAdminParEmailEtPasswordSansEmail(){
        assertThrows(AdministrateurException.class, ()-> service.trouverAdminParEmailEtPassword(null, "P@ssword1"));
    }
    @Test
    void testTrouverAdminParEmailEtPasswordSansPassword(){
        assertThrows(AdministrateurException.class, ()-> service.trouverAdminParEmailEtPassword("emailsympa@email.com", null));
    }
    @Test
    void testTrouverAdminParEmailEtPasswordMauvaisPassword(){
        assertThrows(AdministrateurException.class, ()-> service.trouverAdminParEmailEtPassword("emailsympa@email.com", "P@ss"));
    }
    @Test
    void testTrouverAdminParEmailEtPasswordOk(){
        Administrateur admin1 = adminProfilPourTests();
        AdministrateurResponseDTO adminResponseDTO= adminResponseProfilPourTests2();
        Mockito.when(daoMock.findByEmailAndPassword("sympa@email.com","P@ssword1")).thenReturn(Optional.of(admin1));
        Mockito.when(mapperMock.toAdministrateurResponseDTO(admin1)).thenReturn(adminResponseDTO);
        assertEquals(adminResponseDTO,service.trouverAdminParEmailEtPassword("sympa@email.com","P@ssword1"));
        Mockito.verify(daoMock, Mockito.times(1)).findByEmailAndPassword("sympa@email.com","P@ssword1");
    }



    @Test
    void testSupprimerSansEmail(){
        assertThrows(AdministrateurException.class, () -> service.supprimer(null, "P@ssword1"));
    }

    @Test
    void testSupprimerSansMotDePasse(){
        assertThrows(AdministrateurException.class, () -> service.supprimer("email1@email.com", "null"));
    }
    @Test
    void testSupprimerDernierAdmin(){
        Administrateur administrateur1 = adminProfilPourTests();
        Mockito.when(daoMock.count()).thenReturn(1L);
        Mockito.when(daoMock.findByEmailAndPassword(administrateur1.getEmail(),administrateur1.getPassword())).thenReturn(Optional.of(administrateur1));
        assertThrows(AdministrateurException.class, () -> service.supprimer("ceciestuneemail@email.com", "P@ssword2"));

    }

    @Test
    void testSupprimerOk(){
        Administrateur administrateur1 = adminProfilPourTests();
        Mockito.when(daoMock.findByEmailAndPassword(administrateur1.getEmail(),administrateur1.getPassword())).thenReturn(Optional.of(administrateur1));
        service.supprimer(administrateur1.getEmail(),administrateur1.getPassword());
        Mockito.verify(daoMock, Mockito.times(1)).delete(administrateur1);

    }


    @Test
    void testModifierSansEmail(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand", "Joe", "P@ssword1", "sympa@email.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.modifier(null, "P@ssword1", dto ));
    }

    @Test
    void testModifierSansPassword(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand", "Joe", "P@ssword1", "sympa@email.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.modifier("sympa@email.com", null, dto ));
    }

    @Test
    void testModifierSansRequestDTO(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand", "Joe", "P@ssword1", "sympa@email.com","Administrateur");
        assertThrows(AdministrateurException.class, ()-> service.modifier("sympa@email.com", "P@ssword1", null ));
    }

    @Test
    void testModifierSansFonction(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO("Legrand", "Joe", "P@ssword1", "sympa@email.com",null);
        assertThrows(AdministrateurException.class, ()-> service.modifier("sympa@email.com", "P@ssword1", dto ));
    }

    @Test
    void testModifierOk(){
        Administrateur admin =  adminProfilPourTests();
        Mockito.when(mapperMock.toAdministrateur(adminRequestDTOUn())).thenReturn(admin);
        Mockito.when(daoMock.findByEmailAndPassword(admin.getEmail(),admin.getPassword())).thenReturn(Optional.of(admin));
        service.modifier(admin.getEmail(),admin.getPassword(),adminRequestDTOUn());
    }
    /* *********************************************** *
     *                                                 *
     *                 methodes privées                *
     *                                                 *
     * *********************************************** *
     */

    private AdministrateurRequestDTO adminRequestDTOUn(){
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO( "Legrand", "Joe",  "P@ssword1", "blablabla@mail.com","Administrateur");
        return dto;
    }

    private static Administrateur adminProfilPourTests() {
        Administrateur a = new Administrateur();
        a.setNom("Legrand");
        a.setPrenom("Joe");
        a.setPassword("P@ssword2");
        a.setEmail("ceciestuneemail@email.com");
        a.setFonction("Administrateur1");
        return a;
    }

    private static AdministrateurResponseDTO creerAdminResponseDTOExample(){
        return new AdministrateurResponseDTO("Legrand","Joe","ceciestuneemail@email.com","Administrateur1");
    }

    private static AdministrateurResponseDTO creerAdminResponseDTOExample2(){
        return new AdministrateurResponseDTO("Joestar","Johnathan","blablabla@mail.com","Administrateur2");
    }

    private static AdministrateurResponseDTO adminResponseProfilPourTests2() {
        AdministrateurResponseDTO a = new AdministrateurResponseDTO("Joestar","Johnathan","sympa@email.com","Administrateur");
        return a;
    }

}