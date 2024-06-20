package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.PanneAvecRessource;
import com.ecommerce.library.model.Ressource;
import com.ecommerce.library.repository.PanneAvecRessourceRepository;
import com.ecommerce.library.repository.RessourceRepository;
import com.ecommerce.library.service.RessourceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class RessourceServiceImpl implements RessourceService {

    private final RessourceRepository ressourceRepository;
    private final PanneAvecRessourceRepository PanneAvecRessourceRepo;


    @Override
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    @Override
    public List<Ressource> getRessourcesByMembreDepartement(String id) {
        return ressourceRepository.getRessourceByIdMembreDepartement(id);
    }

    @Override
    public Ressource addRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }


    @Override
    public List<Ressource> addMultipleRessources(List<Ressource> ressources) {
        return ressourceRepository.saveAll(ressources);
    }

    @Override
    public Ressource updateRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    @Override
    public void deleteRessource(Long id) {
        Ressource ressource = ressourceRepository.findById(id).orElseThrow();
        ressource.setIsDeleted(true);
        ressourceRepository.save(ressource);
    }

    @Override
    public List<Ressource> getRessourcesByDepartement(Long id) {
        return ressourceRepository.getRessourceByIdDepartement(id);
    }
    public Optional<Ressource> getRessourcesById(Long id) {
        return ressourceRepository.findById(id);
    }
    public Ressource getRessourcesByIdAndIsdeleted(Long id) {
        return ressourceRepository.findRessourceByIdAndIsDeletedFalse(id);
    }



    @Override
    public List<Ressource> getByIsDeteletedFalse() {

        return ressourceRepository.findRessourceByIsDeletedFalse();
    }

    @Override
    public List<Ressource> listRessourcesLivrees() {
        return ressourceRepository.findAllByCodeBarreIsNull();
    }

    @Override
    public List<Ressource> listRessourcesDisponibles() {
        return ressourceRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull();
    }
    @Override
    public List<PanneAvecRessource> getRessouceId(List<Ressource> ressources) {
        List<PanneAvecRessource> pannesAvecRessource = new ArrayList<>();
        for (Ressource ressource : ressources) {
            Long ressourceId = ressource.getId();
            PanneAvecRessource optionalPanneAvecRessource = PanneAvecRessourceRepo.findPanneAvecRessourceByIdRessource(ressourceId);


            pannesAvecRessource.add(optionalPanneAvecRessource);


        }
        return pannesAvecRessource;
    }
    @Override
    public Long findIdByCodeBarre(String codeBarre) {
        return ressourceRepository.findByCodeBarre(codeBarre).getId();
    }

    @Override
    public Long getRessourceIdByCodeBarre(String codeBarre) {
        Ressource ressource = ressourceRepository.findByCodeBarre(codeBarre);
        if (ressource != null) {
            return ressource.getId();
        } else {
            // Gérer le cas où la ressource n'est pas trouvée
            return null;
        }
    }
}