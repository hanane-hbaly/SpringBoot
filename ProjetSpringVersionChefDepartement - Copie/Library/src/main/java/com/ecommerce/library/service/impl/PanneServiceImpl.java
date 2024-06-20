package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Panne;
import com.ecommerce.library.model.PanneAvecRessource;
import com.ecommerce.library.model.Ressource;
import com.ecommerce.library.repository.PanneAvecRessourceRepository;
import com.ecommerce.library.repository.PanneRepository;
import com.ecommerce.library.repository.RessourceRepository;

import com.ecommerce.library.service.PanneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor

public class PanneServiceImpl implements PanneService {

    private final PanneRepository panneRepository;
    private final RessourceRepository ressourceRepository;
    private final PanneAvecRessourceRepository PanneAvecRessourceRepo;
    @Override
    public Panne addPanne(Panne panne) {
        return panneRepository.save(panne);
    }

    @Override
    public Panne updatePanne(Panne panne) {
        return panneRepository.save(panne);
    }

    @Override
    public void deletePanne(Long id) {
        panneRepository.deleteById(id);
    }

    @Override
    public List<Panne> getAllPannes() {
        return panneRepository.findAll();
    }

    @Override
    public List<Panne> getPannesMembreDepartement(String id) {
        return panneRepository.findPanneByIdMembreDepartement(id);
    }

    @Override
    public List<Panne> getPanneWithConstatNotNullAndDemandeNull() {
        return panneRepository.findByConstatIsNotNullAndDemandeIsNull();
    }

    @Override
    public List<Panne> getPannesNotTreated() {
        return panneRepository.findPanneByIsTreatedFalse();
    }


    @Override
    public List<Panne> getPanneWithDemandeNotNull() {
        return panneRepository.findByDemandeIsNotNull();
    }

    @Override
    public List<PanneAvecRessource> getNotTreatedPanne() {
        List<Panne> pannes = panneRepository.findAllByIsTreatedFalse();
        List<Ressource> ressources = ressourceRepository.findAll();

        List<PanneAvecRessource> panneAvecRessourceList = pannes.stream()
                .map(panne -> {
                    Ressource ressource = ressources.stream()
                            .filter(r -> r.getId() == panne.getIdRessource())
                            .findFirst()
                            .orElse(null);
                    PanneAvecRessource panneAvecRessource = PanneAvecRessource.builder()
                            .idPanne(panne.getId())
                            .idRessource(ressource == null ? null : ressource.getId())
                            .idMembreDepartement(ressource == null ? null :ressource.getIdMembreDepartement())
                            .dateApparition(panne.getDateApparition())
                            .type(ressource == null ? null : ressource.getType())
                            .marque(ressource == null ? null : ressource.getMarque())
                            .build();
                    return panneAvecRessource;
                })
                .collect(Collectors.toList());
        return panneAvecRessourceList;
    }
    @Override
    public List<PanneAvecRessource> getRessourcesForPanneId(List<Panne> pannes) {
        List<PanneAvecRessource> pannesAvecRessource = new ArrayList<>();
        for (Panne panne : pannes) {
            Long panneId = panne.getId();
            Optional<PanneAvecRessource> optionalPanneAvecRessource = PanneAvecRessourceRepo.findById(panneId);
            if (optionalPanneAvecRessource.isPresent()) {

                pannesAvecRessource.add(optionalPanneAvecRessource.get());
            } else {
                // Gérer le cas où aucune panne avec ressource correspondante n'est trouvée
                throw new RuntimeException("Panne avec ressource non trouvée pour l'ID de panne: " + panneId);
            }
        }
        return pannesAvecRessource;
    }
    public void savePanneWithDetails(Date dateApparition, Long idRessource, String idMembreDepartement) {
        // Convertir java.util.Date en java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(dateApparition.getTime());

        Panne panne = new Panne();
        panne.setDateApparition(sqlDate);
        panne.setIdRessource(idRessource);
        panne.setIdMembreDepartement(idMembreDepartement);
        panneRepository.save(panne);
    }
}