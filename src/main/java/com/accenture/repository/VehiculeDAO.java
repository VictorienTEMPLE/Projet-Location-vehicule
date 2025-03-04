package com.accenture.repository;

import com.accenture.repository.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VehiculeDAO extends JpaRepository<Vehicule, Integer> {
    List<Vehicule> findByCrenauxReservationDebut(LocalDateTime dateheuredebut);
    List<Vehicule> findByCrenauxReservationFin(LocalDateTime dateheurefin);
}
