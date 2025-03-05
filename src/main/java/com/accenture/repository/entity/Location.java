package com.accenture.repository.entity;

import com.accenture.shared.EtatLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER )
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Vehicule vehicule;
    //private Accessoires accessoires;
    private LocalDate dateDeDebut;
    private LocalDate dateDeFin;
    private int nbkilometreParcourus;
    private int montantTotalCalcule;
    private EtatLocation etatLocation;
}
