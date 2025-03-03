package com.accenture.repository.entity;

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
@DiscriminatorValue("Utilitaire")
public class Utilitaire extends Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String modele;
    private int nbPlaces;
    private int nbBagages;
    private double chargeMax;
    private int ptac;
    private int capacite;
    private Transmission transmission;
    private TypeCarburant carburant;
    private TypeVehicule typeVehicule;

    public Utilitaire(String modele, int nbPlaces, int nbBagages, double chargeMax, int ptac, int capacite, Transmission transmission, TypeCarburant carburant, TypeVehicule typeVehicule) {
        this.modele = modele;
        this.nbPlaces = nbPlaces;
        this.nbBagages = nbBagages;
        this.chargeMax = chargeMax;
        this.ptac = ptac;
        this.capacite = capacite;
        this.transmission = transmission;
        this.carburant = carburant;
        this.typeVehicule = typeVehicule;
    }
}
