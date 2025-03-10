package com.accenture.repository.entity;


import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("Voiture")
public class Voiture extends Vehicule{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String modele;
    private int nbPlaces;
    private int nbBagages;
    private int nbPortes;
    private Transmission transmission;
    private TypeCarburant carburant;
    private TypeVehicule voiture;

    public Voiture(String modele, int nbPlaces, int nbBagages, int nbPortes, Transmission transmission, TypeCarburant carburant, TypeVehicule voiture) {
        this.modele = modele;
        this.nbPlaces = nbPlaces;
        this.nbBagages = nbBagages;
        this.nbPortes = nbPortes;
        this.transmission = transmission;
        this.carburant = carburant;
        this.voiture = voiture;
    }
}
