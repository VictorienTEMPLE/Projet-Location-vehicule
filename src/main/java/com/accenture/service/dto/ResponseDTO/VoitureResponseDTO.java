package com.accenture.service.dto.ResponseDTO;

import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

public record VoitureResponseDTO(
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
