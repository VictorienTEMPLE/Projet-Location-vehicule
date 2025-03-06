package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.VehiculeDAO;
import com.accenture.repository.entity.Vehicule;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.mapper.VehiculeMapper;
import com.accenture.shared.FiltreRechercheVehicule;

import java.time.LocalDateTime;
import java.util.List;

public class VehiculeServiceImpl implements VehiculeService {
    private final VehiculeDAO vehiculeDAO;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeServiceImpl(VehiculeDAO vehiculeDAO, VehiculeMapper vehiculeMapper) {
        this.vehiculeDAO = vehiculeDAO;
        this.vehiculeMapper = vehiculeMapper;
    }
}

