package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Demande;
import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.repository.DemandeRepository;
import com.ecommerce.library.repository.MembresDepartementRepository;
import com.ecommerce.library.service.DemandeService;
import com.ecommerce.library.service.MembresDepartementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;
    private final MembresDepartementRepository membreDepartementRepository;
    private final MembresDepartementService membreDepartementService;

//    @Override
//    public void createDemande(String userId) {
//        Optional<MembreDepartement> chefDepartement = membreDepartementRepository.findById(Long.valueOf(userId));
//        if (chefDepartement.isPresent()) {
//            Long idDepartement = chefDepartement.get().getDepartement().getId();
//            List<MembreDepartement> membresDepartement = membreDepartementService.getMembresByIdDepartement(idDepartement);
//
//            List<Demande> demandes = membresDepartement.stream()
//                    .filter(membreDepartement -> !membreDepartement.getId().equals(Long.valueOf(userId)))
//                    .map(membre -> createDemandeObject(String.valueOf(membre.getId()), idDepartement))
//                    .collect(Collectors.toList());
//
//            demandeRepository.saveAll(demandes);
//        }
//    }

    private Demande createDemandeObject(String membreId, Long idDepartement) {
        return Demande.builder()
                .message("Envoyez vos besoins")
                .dateDemande(Date.valueOf(LocalDate.now()))
                .idDepartement(idDepartement)
                .idMembreDepartement(membreId)
                .isSeen(false)
                .build();
    }


    @Override
    public Demande demandeSeen(Long id) {
        Optional<Demande> demandeOpt = demandeRepository.findById(id);
        if (demandeOpt.isPresent()) {
            Demande demande = demandeOpt.get();
            demande.setIsSeen(true);
            return demandeRepository.save(demande);
        } else {
            // Gérer le cas où la demande avec l'ID spécifié n'est pas présente
            return null; // Ou lancez une exception, selon votre logique de gestion des erreurs
        }
    }


    @Override
    public List<Demande> getAllDemandesByMembreId(String id) {
        return demandeRepository.findAllByIdMembreDepartement(id);
    }

    @Override
    public List<Demande> getAllDemandesByIdDepartement(Long id) {
        return demandeRepository.findAllByIdDepartement(id);
    }

    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @Override
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }
}