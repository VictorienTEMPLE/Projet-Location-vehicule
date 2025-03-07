package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("Client")
public class Client extends UtilisateurConnecte{

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription;
    private Permis listePermis;
    private Boolean desactive;
}
