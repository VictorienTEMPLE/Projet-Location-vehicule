package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

import java.util.HashSet;

public record UtilitaireResponseDTO(
        String modele,
        String marque,
        String couleur,
        int nbPlaces,
        int nbBagages,
        double chargeMax,
        int ptac,
        int capacite,
        Transmission transmission,
        TypeCarburant carburant,
        TypeVehicule typeVehicule,
        Boolean climatisation,
        Boolean retireDuParc,
        Boolean actif
) {
}
