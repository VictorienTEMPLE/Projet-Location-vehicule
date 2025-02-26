package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Discriminator")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String marque;
    private String typeVehicule;
    private String couleur;
    private int nbPlaces;
    private int kilometrage;
    private Boolean actif;
    private Boolean retireDuParc;
    private Boolean climatisation;
    private double TarifJournalierDeBase;

}
