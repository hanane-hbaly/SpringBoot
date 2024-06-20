package com.ecommerce.library.repository;

import com.ecommerce.library.model.NotifEnseignant;
import com.ecommerce.library.model.NotifFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifEnseignantRepository extends JpaRepository<NotifEnseignant, Long> {



}
