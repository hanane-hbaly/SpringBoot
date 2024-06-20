package com.ecommerce.library.repository;

import com.ecommerce.library.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findAllByIdMembreDepartement(String id);

    List<Demande> findAllByIdDepartement(Long id);


}
