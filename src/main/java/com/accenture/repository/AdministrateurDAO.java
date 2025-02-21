package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurDAO extends JpaRepository<Administrateur, String> {
}
