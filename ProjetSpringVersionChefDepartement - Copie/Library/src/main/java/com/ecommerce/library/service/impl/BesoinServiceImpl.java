package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Besoin;
import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.repository.BesoinRepository;
import com.ecommerce.library.repository.ImprimanteRepository;
import com.ecommerce.library.repository.MembresDepartementRepository;
import com.ecommerce.library.repository.OrdinateurRepository;
import com.ecommerce.library.service.BesoinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BesoinServiceImpl implements BesoinService {

    private final BesoinRepository besoinRepository;
    private final OrdinateurRepository ordinateurRepository;
    private final ImprimanteRepository imprimanteRepository;
    private final MembresDepartementRepository membreDepartementRepository;

    @Override
    public Besoin saveBesoin(Besoin besoin, String userId) {
        Optional<MembreDepartement> membreOptional = membreDepartementRepository.findById(Long.parseLong(userId));

        if (membreOptional.isPresent()) {
            MembreDepartement membre = membreOptional.get();
            besoin.setIdMembreDepartement(String.valueOf(membre.getId()));
            besoin.setIdDepartement(membre.getDepartement().getId());
            besoin.setDateDemande(new Date());
            return besoinRepository.save(besoin);
        } else {
            // Gérer le cas où aucun membre de département n'est trouvé pour cet utilisateur
            throw new IllegalArgumentException("Aucun membre de département trouvé pour l'utilisateur avec l'ID : " + userId);
        }
    }

    @Override
    public List<Besoin> getAllBesoins() {
        return besoinRepository.findAll();
    }

    @Override
    public List<Besoin> getBesoinsByDepartement(String id) {
        Optional<MembreDepartement> membreOptional = membreDepartementRepository.findById(Long.parseLong(id));

        if (membreOptional.isPresent()) {
            MembreDepartement membre = membreOptional.get();
            return besoinRepository.findBesoinByIdDepartement(membre.getDepartement().getId());
        } else {
            // Gérer le cas où aucun membre de département n'est trouvé pour cet ID
            throw new IllegalArgumentException("Aucun membre de département trouvé pour l'ID : " + id);
        }
    }


    @Override
    public List<Besoin> getBesoinsByMembreDepartement(String id) {
        return besoinRepository.findBesoinByIdMembreDepartement(id);
    }

    @Override
    public List<Besoin> getBesoinsDepartementNotInAppelOffre(Long id) {
        return besoinRepository.findBesoinByIdDepartementAndIsBesoinInAppelOffreIsFalse(id);
    }

    @Override
    public Besoin updateBesoin(Besoin besoin) {
        return besoinRepository.save(besoin);
    }

    @Override
    public Besoin getBesoinsDisponiblesByIdMembre(String id) {
        Optional<Besoin> besoin = besoinRepository.findBesoinByIdMembreDepartementAndIsBesoinInAppelOffreIsFalse(id);

        return besoin.get();
    }

    @Override
    public void besoinAddedInAppelOffre(Long id) {
        Optional<Besoin> besoin = besoinRepository.findById(id);

        besoin.get().setBesoinInAppelOffre(true);
        besoinRepository.save(besoin.get());
    }

    @Override
    public List<Besoin> getBesoinsNotInAppelOffre() {
        return besoinRepository.findAllByIsBesoinInAppelOffreIsFalse();
    }

    @Override
    public List <Besoin> getBesoinsDepartementNotInAppelOffreandNotAffected(Long id) { return besoinRepository.findBesoinByIdDepartementAndIsAffectedIsFalseAndIsBesoinInAppelOffreIsFalse(id);}

    @Override
    public void deleteBesoinOfMembre(String id) {
        List<Besoin> besoins = besoinRepository.findBesoinByIdMembreDepartementAndIsAffectedIsFalse(id);
        besoinRepository.deleteAll(besoins);
    }

    @Override
    public void deleteBesoin(Long id) {
        Optional<Besoin> besoin = besoinRepository.findById(id);

        besoinRepository.deleteById(id);
    }

}
