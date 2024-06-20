package com.ecommerce.library.repository;

import com.ecommerce.library.model.RessourceAvecFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceFournisseurRepository extends JpaRepository<RessourceAvecFournisseur,Long> {
}
