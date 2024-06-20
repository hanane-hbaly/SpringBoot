package com.ecommerce.library.service;


import com.ecommerce.library.model.Demande;

import java.util.List;

public interface DemandeService {

//    void createDemande(String userId);


    Demande demandeSeen(Long id);

    List<Demande> getAllDemandesByMembreId(String id);

    List<Demande> getAllDemandesByIdDepartement(Long id);

    List<Demande> getAllDemandes();

    void deleteDemande(Long id);

}
