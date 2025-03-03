package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

import java.util.HashSet;

public record VoitureResponseDTO(
        String modele,
        int nbPlaces,
        int nbBagages,
        Transmission transmission,
        TypeCarburant carburant,
        TypeVehicule typeVehicule,
        Permis permis
) {
}
