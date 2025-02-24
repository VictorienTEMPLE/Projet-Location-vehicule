package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("Client")
public class Client extends UtilisateurConnecte{

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription;
    private HashSet<Permis> listePermis;
    private Boolean desactive;
}
