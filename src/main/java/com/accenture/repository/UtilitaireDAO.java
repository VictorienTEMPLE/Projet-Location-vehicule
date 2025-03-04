package com.accenture.repository;

import com.accenture.repository.entity.Utilitaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilitaireDAO extends JpaRepository<Utilitaire,Integer> {
    List<Utilitaire> findByActif(boolean actif);
    List<Utilitaire> findByRetireDuParc(boolean retireDuParc);
}
