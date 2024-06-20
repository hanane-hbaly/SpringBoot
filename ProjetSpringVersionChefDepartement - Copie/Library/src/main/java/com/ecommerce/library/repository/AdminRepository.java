package com.ecommerce.library.repository;

import com.ecommerce.library.model.Departement;
import com.ecommerce.library.model.Role;
import com.ecommerce.library.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    @Query("SELECT u.id FROM Users u WHERE u.role = :role")
    List<Long> findIdsByRole(@Param("role") Role role);

}
