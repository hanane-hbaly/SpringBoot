package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.DepartementDto;
import com.ecommerce.library.model.Departement;
import com.ecommerce.library.repository.DepartementRepository;
import com.ecommerce.library.service.DepartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements DepartementService {
    private final DepartementRepository departementRepository;

    @Override
    public Departement save(Departement departement) {
        Departement departementSave = new Departement();
        departementSave.setNomDepartement(departement.getNomDepartement());

        departementSave.setMembreDepartements(departement.getMembreDepartements());
        return departementRepository.save(departementSave);

    }

    @Override
    public Departement update(Departement departement) {
        Departement departementUpdate = departementRepository.getReferenceById(departement.getId());
        departementUpdate.setNomDepartement(departement.getNomDepartement());
        System.out.println("La valeur  modifié : " + departementUpdate);
        return departementRepository.save(departementUpdate);
    }




    @Override
    public List<Departement> findALl() {
        System.out.println("La valeur de la variable est : " + departementRepository.findAll());
        return departementRepository.findAll();

    }

    @Override
    public Optional<Departement> findById(Long id) {

        return departementRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Departement> departement = departementRepository.findById(id);

        System.out.println("La valeur  modifié : " + departement);
        departementRepository.deleteById(id);
    }





}


