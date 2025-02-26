package com.accenture.repository.entity;


import com.accenture.shared.Permis;
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
    private HashSet<TypeCarburant> carburant;
    private HashSet<TypeVehicule> voiture;
    private HashSet<Permis> permis;

    public Voiture(String modele, int nbPlaces, int nbBagages, HashSet<TypeCarburant> carburant, HashSet<TypeVehicule> voiture, HashSet<Permis> permisB) {
        this.modele = modele;
        this.nbPlaces = nbPlaces;
        this.nbBagages = nbBagages;
        this.carburant = carburant;
        this.voiture = voiture;
        this.permis = permis;
    }
}
