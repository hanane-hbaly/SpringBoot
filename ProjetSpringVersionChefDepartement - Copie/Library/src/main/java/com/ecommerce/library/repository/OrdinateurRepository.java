package com.ecommerce.library.repository;

import com.ecommerce.library.model.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long> {

    List<Ordinateur> getOrdinateurByIdMembreDepartement(String id);
    List<Ordinateur> getOrdinateurByIdDepartement(Long id);
    List<Ordinateur> getOrdinateurByIdFournisseur(String id);
    List<Ordinateur> findAllByCodeBarreIsNotNull();
    List<Ordinateur> findAllByCodeBarreIsNullAndMarqueIsNotNull();

}
