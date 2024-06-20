package com.ecommerce.library.repository;

import com.ecommerce.library.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    @Query(value = "update Departement  set nomDepartement = ?1 where id = ?2")
    Departement update(String nomDepartement, Long id);

//    Departement getDepartementById(Long id);
    Departement findDepartementById(Long id);

    Departement getById(Long id);

}
