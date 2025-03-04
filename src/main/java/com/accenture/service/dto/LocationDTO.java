package com.accenture.service.dto;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Vehicule;
import com.accenture.shared.EtatLocation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public record LocationDTO (
     Client client,
     Vehicule vehicule,
    // Accessoires accessoires;
     LocalDate dateDeDebut,
     LocalDate dateDeFin,
     int NbkilometreParcourus,
     int MontantTotalCalcule,
     EtatLocation etatLocation
){

}
