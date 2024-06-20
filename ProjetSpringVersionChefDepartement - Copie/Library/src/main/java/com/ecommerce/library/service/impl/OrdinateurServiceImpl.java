package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Imprimante;
import com.ecommerce.library.model.Ordinateur;
import com.ecommerce.library.repository.OrdinateurRepository;
import com.ecommerce.library.service.OrdinateurService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class OrdinateurServiceImpl implements OrdinateurService {

    private final OrdinateurRepository ordinateurRepository;


    @Override
    public Ordinateur addOrdinateur(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    @Override
    public List<Ordinateur> getAllOrdinateurs() {
        List<Ordinateur> ordinateurs = ordinateurRepository.findAll();

        return ordinateurs.stream()
                .filter(ordinateur -> !ordinateur.getIsAffected())
                .collect(Collectors.toList());
    }


    @Override
    public List<Ordinateur> getOrdinateursByMembreDepartement(String id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdMembreDepartement(id);
        return filterOrdinateurs(ordinateurs);
    }

    @Override
    public List<Ordinateur> getOrdinateursByDepartement(Long id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdDepartement(id);
        return filterOrdinateurs(ordinateurs);
    }

    @Override
    public List<Ordinateur> getOrdinateursByFournisseur(String id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdFournisseur(id);
        return filterOrdinateurs(ordinateurs);
    }

    @Override
    public Optional<Ordinateur> getOrdinateur(Long id) {
        Optional<Ordinateur> ordinateur = ordinateurRepository.findById(id);

        return ordinateur;
    }

    @Override
    public Ordinateur updateOrdinateur(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    @Override
    public void deleteOrdinateur(Long id) {
        Optional<Ordinateur> ordinateurOptional = this.getOrdinateur(id);

        if (ordinateurOptional.isPresent()) {
            Ordinateur ordinateur = ordinateurOptional.get();
            ordinateur.setIsDeleted(true);
            ordinateurRepository.save(ordinateur);
        }
    }

    public List<Ordinateur> filterOrdinateurs(List<Ordinateur> ordinateurs) {
        List<Ordinateur> filteredOrdinateurs = ordinateurs.stream()
                .filter(ordinateur -> "Ordinateur".equals(ordinateur.getType()))
                .filter(ordinateur -> !ordinateur.getIsDeleted())
                .toList();
        return filteredOrdinateurs;


    }

    @Override
    public List<Ordinateur> getOrdinateurLivrees() {
        List<Ordinateur> ordinateurNonLivre = filterOrdinateurs(ordinateurRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull());
        return ordinateurNonLivre;
    }

    @Override
    public List<Ordinateur> getOrdinateurDisponibles() {
        List<Ordinateur> ordinateurDisponible = filterOrdinateurs(ordinateurRepository.findAllByCodeBarreIsNotNull());
        return ordinateurDisponible;
    }

}
