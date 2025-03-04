package com.accenture.repository;

import com.accenture.repository.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureDAO extends JpaRepository<Voiture, Integer> {
    List<Voiture> findByActif(boolean actif);
    List<Voiture> findByRetireDuParc(boolean retireDuParc);

}
