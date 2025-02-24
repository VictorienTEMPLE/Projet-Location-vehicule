package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurDAO extends JpaRepository<Administrateur, String> {
    boolean existsByEmail(String email);
    Optional<Administrateur> findByEmailAndPassword(String email, String password);
}
