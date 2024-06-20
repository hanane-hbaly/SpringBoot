package com.ecommerce.library.service;

import com.ecommerce.library.model.PanneAvecRessource;
import com.ecommerce.library.model.Ressource;

import java.util.List;
import java.util.Optional;

public interface RessourceService {

    List<Ressource> getAllRessources();
    List<Ressource> getRessourcesByMembreDepartement(String id);
    Ressource addRessource(Ressource ressource);


    List<Ressource> addMultipleRessources(List<Ressource> ressources);
    Ressource updateRessource(Ressource ressource);
    void deleteRessource(Long id);
    List<Ressource> getRessourcesByDepartement(Long id);

    public List<Ressource> listRessourcesLivrees();

    public List<Ressource> listRessourcesDisponibles();
    public Optional<Ressource> getRessourcesById(Long id);
    public List<Ressource> getByIsDeteletedFalse();
    public List<PanneAvecRessource> getRessouceId(List<Ressource> ressources);
    public Ressource getRessourcesByIdAndIsdeleted(Long id);
    Long getRessourceIdByCodeBarre(String codeBarre);
    Long findIdByCodeBarre(String codeBarre);


}