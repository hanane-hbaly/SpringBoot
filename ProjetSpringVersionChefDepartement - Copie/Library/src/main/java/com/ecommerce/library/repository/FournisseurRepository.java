package com.ecommerce.library.repository;

import com.ecommerce.library.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
    Optional<Fournisseur> findByUsername(String userName);

    Fournisseur findFournisseurById(Long id);
}
