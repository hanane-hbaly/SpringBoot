package com.ecommerce.library.service;

import com.ecommerce.library.model.Panne;
import com.ecommerce.library.model.PanneAvecRessource;

import java.util.List;
import java.util.Optional;

public interface PanneService {
    Panne addPanne(Panne panne);
    Panne updatePanne(Panne panne);
    void deletePanne(Long id);
    List<Panne> getAllPannes();
    List<Panne> getPannesMembreDepartement(String id);
    List<Panne> getPanneWithConstatNotNullAndDemandeNull();
    List<Panne> getPannesNotTreated();
    List<Panne> getPanneWithDemandeNotNull();

    List<PanneAvecRessource> getNotTreatedPanne();


    List<PanneAvecRessource> getRessourcesForPanneId(List<Panne> panne);
    void savePanneWithDetails(java.util.Date dateApparition, Long idRessource, String idMembreDepartement);

}