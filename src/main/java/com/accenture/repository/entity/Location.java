package com.accenture.repository.entity;

import com.accenture.shared.EtatLocation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class Location {
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Vehicule vehicule;
    //private Accessoires accessoires;
    private LocalDate dateDeDebut;
    private LocalDate dateDeFin;
    private int NbkilometreParcourus;
    private int MontantTotalCalcule;
    private EtatLocation etatLocation;
}
