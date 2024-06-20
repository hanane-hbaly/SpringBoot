package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Imprimante;
import com.ecommerce.library.repository.ImprimanteRepository;
import com.ecommerce.library.service.ImprimanteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class ImprimanteServiceImpl implements ImprimanteService {

    private final ImprimanteRepository imprimanteRepository;

    @Override
    public Imprimante saveImprimante(Imprimante imprimante) {
        return imprimanteRepository.save(imprimante);
    }

    @Override
    public List<Imprimante> getAllImprimantes() {
        List<Imprimante> imprimantes = imprimanteRepository.findAll();


        return imprimantes.stream()
                .filter(imprimante -> !imprimante.getIsAffected())
                .collect(Collectors.toList());
    }

    @Override
    public List<Imprimante> getImprimantesByMembreDepartement(String id) {
        List<Imprimante> imprimantesDisponibles = imprimanteRepository.getImprimanteByIdMembreDepartement(id)
                .stream()
                .filter(imprimante -> !imprimante.getIsDeleted())
                .collect(Collectors.toList());

        return imprimantesDisponibles;
    }

    @Override
    public List<Imprimante> getImprimantesByDepartement(Long id) {
        List<Imprimante> imprimantesDisponibles = imprimanteRepository.getImprimanteByIdDepartement(id)
                .stream()
                .filter(imprimante -> !imprimante.getIsDeleted())
                .collect(Collectors.toList());

        return imprimantesDisponibles;
    }

    @Override
    public List<Imprimante> getImprimantesByFournisseur(String id) {
        List<Imprimante> imprimantesDisponibles = imprimanteRepository.getImprimanteByIdFournisseur(id)
                .stream()
                .filter(imprimante -> !imprimante.getIsDeleted())
                .collect(Collectors.toList());

        return imprimantesDisponibles;
    }

    @Override
    public Imprimante getImprimante(Long id) {
        Optional<Imprimante> imprimante = imprimanteRepository.findById(id);



        return imprimante.get();

    }

    @Override
    public Imprimante updateImprimante(Imprimante imprimante) {
        return imprimanteRepository.save(imprimante);
    }

    @Override
    public void deleteImprimante(Long id) {
        Imprimante imprimante = this.getImprimante(id);
        imprimante.setIsDeleted(true);
        imprimanteRepository.save(imprimante);
    }

    public List<Imprimante> filterImprimantes(List<Imprimante> imprimantes) {
        List<Imprimante> imprimanteList = imprimantes.stream()
                .filter(imprimante -> "Imprimante".equals(imprimante.getType()))
                .filter(imprimante -> !imprimante.getIsDeleted())
                .toList();

        return imprimanteList;
    }

    @Override
    public List<Imprimante> getImprimantesLivrees() {
        List<Imprimante> imprimantesLivrees = imprimanteRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull()
                .stream()
                .filter(imprimante -> !imprimante.getIsDeleted())
                .collect(Collectors.toList());

        return imprimantesLivrees;

    }

    @Override
    public List<Imprimante> getImprimantesDisponibles() {

        List<Imprimante> imprimanteDisponible = imprimanteRepository.findAllByCodeBarreIsNotNull()
                .stream()
                .filter(imprimante -> !imprimante.getIsDeleted())
                .collect(Collectors.toList());

        return imprimanteDisponible;
    }

}
