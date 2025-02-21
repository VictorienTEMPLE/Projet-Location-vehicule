package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Discriminator")
public class UtilisateurConnecte {
    @Id
    private String email;
    private String nom;
    private String prenom;
    private String password;


    public UtilisateurConnecte(String nom, String prenom, String email, String password) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
    }
}
