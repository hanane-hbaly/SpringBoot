package com.ecommerce.library.repository;

import com.ecommerce.library.model.AppelOffre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppelOffreRepository extends JpaRepository<AppelOffre,Long> {
    Optional<AppelOffre> findAppelOffreById(Long id);
    List<AppelOffre> findAppelOffreByIsAffectedNull();
}


