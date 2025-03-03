package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

import java.util.HashSet;

public record UtilitaireResponseDTO(
        String modele,
        int nbPlaces,
        int nbBagages,
        double chargeMax,
        int ptac,
        int capacite,
        TypeCarburant carburant,
        TypeVehicule typeVehicule,
        Permis permis
) {
}
