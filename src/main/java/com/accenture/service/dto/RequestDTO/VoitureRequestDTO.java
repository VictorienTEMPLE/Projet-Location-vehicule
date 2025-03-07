package com.accenture.service.dto.RequestDTO;

import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

public record VoitureRequestDTO(
    int id,
    String modele,
    String marque,
    String couleur,
    int nbPlaces,
    int nbPortes,
    int nbBagages,
    int kilometrage,
    double tarifJournalierDeBase,
    Transmission transmission,
    TypeCarburant carburant,
    TypeVehicule typeVehicule,
    Boolean climatisation,
    Boolean retireDuParc,
    Boolean actif
     ) {
}
