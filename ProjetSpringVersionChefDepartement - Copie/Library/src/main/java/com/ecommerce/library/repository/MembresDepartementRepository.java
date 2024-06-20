package com.ecommerce.library.repository;


import com.ecommerce.library.model.Departement;
import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembresDepartementRepository extends JpaRepository<MembreDepartement, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE MembreDepartement m SET m.role = ?1 WHERE m.id = ?2")
    void updateMemberRole(Role roleId, Long memberId);

    MembreDepartement findMembreDepartementById(Long id);

    List<MembreDepartement> getMembreDepartementByDepartementId(Long idDepartement);
    List<MembreDepartement> getMembreDepartementByDepartement(Departement departement);
}
