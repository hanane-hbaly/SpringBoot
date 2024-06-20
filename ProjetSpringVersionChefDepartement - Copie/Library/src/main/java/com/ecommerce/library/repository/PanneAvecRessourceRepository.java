package com.ecommerce.library.repository;

import com.ecommerce.library.model.Panne;
import com.ecommerce.library.model.PanneAvecRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanneAvecRessourceRepository extends JpaRepository<PanneAvecRessource, Long> {
    // Vous pouvez ajouter des méthodes supplémentaires spécifiques si nécessaire
    PanneAvecRessource findPanneAvecRessourceByIdRessource(Long idressource);
}