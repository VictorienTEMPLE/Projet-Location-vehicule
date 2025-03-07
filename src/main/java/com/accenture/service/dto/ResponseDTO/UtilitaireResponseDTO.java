package com.accenture.service.dto.ResponseDTO;

import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVehicule;

public record UtilitaireResponseDTO(
        String modele,
        String marque,
        String couleur,
        int nbPlaces,
        int nbBagages,
        double chargeMax,
        double ptac,
        int capacite,
        double tarifJournalierDeBase,
        Transmission transmission,
        TypeCarburant carburant,
        TypeVehicule typeVehicule,
        Boolean climatisation,
        Boolean retireDuParc,
        Boolean actif
) {
}
