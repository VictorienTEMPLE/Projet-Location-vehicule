package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import com.accenture.shared.TypeVehicule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Discriminator")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String marque;
    private String couleur;
    private int kilometrage;
    private Boolean climatisation;
    private Boolean actif;
    private Boolean retireDuParc;
    private double TarifJournalierDeBase;
    private Permis permis;
    private LocalDateTime crenauxReservation;
}
