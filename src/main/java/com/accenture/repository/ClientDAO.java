package com.accenture.repository;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.UtilisateurConnecte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Integer> {
}
