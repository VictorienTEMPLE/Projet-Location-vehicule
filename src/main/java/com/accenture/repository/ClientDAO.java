package com.accenture.repository;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.UtilisateurConnecte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDAO extends JpaRepository<Client, String> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmailAndPassword(String email, String password);
    Optional<Client> findByEmail(String email);
}
