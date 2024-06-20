package com.ecommerce.library.service;

import com.ecommerce.library.dto.DepartementDto;
import com.ecommerce.library.model.Departement;

import java.util.List;
import java.util.Optional;

public interface DepartementService {
    Departement save(Departement departement);

    Departement update(Departement departement);



    List<Departement> findALl();

    Optional<Departement> findById(Long id);

    void deleteById(Long id);




}
