package com.accenture.repository;

import com.accenture.repository.entity.Location;
import com.accenture.service.dto.LocationResponseDTO;
import com.accenture.shared.TypeVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LocationDAO extends JpaRepository<Location,Integer> {
    List<LocationResponseDTO> findByDateDeDebut(LocalDate localDate);
    List<LocationResponseDTO> findByDateDeFin(LocalDate localDate);
    List<LocationResponseDTO> findByVehiculeId(int id);
}
