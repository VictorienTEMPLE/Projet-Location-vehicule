package com.accenture.repository;

import com.accenture.repository.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeDAO extends JpaRepository<Vehicule, Integer> {
}
