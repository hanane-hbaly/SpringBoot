package com.ecommerce.library.service;

import com.ecommerce.library.model.MembreDepartement;

import java.util.List;
import java.util.Optional;

public interface MembresDepartementService {

    MembreDepartement save(MembreDepartement MembresDepartement);

     MembreDepartement update(MembreDepartement MembresDepartement);



    List<MembreDepartement> findALl();



    Optional<MembreDepartement> findById(Long id);

    void deleteById(Long id);

    List<MembreDepartement> getMembresByIdDepartement(Long idDepartement);
}
