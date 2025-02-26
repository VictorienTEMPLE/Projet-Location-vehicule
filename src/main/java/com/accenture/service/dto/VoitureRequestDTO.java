package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

import java.util.HashSet;

public record VoitureRequestDTO(
    int id,
     String modele,
     int nbPlaces,
     int nbBagages,
     HashSet<TypeCarburant> carburant,
     HashSet<TypeVehicule> voiture,
     HashSet<Permis> permisB
     ) {
}
