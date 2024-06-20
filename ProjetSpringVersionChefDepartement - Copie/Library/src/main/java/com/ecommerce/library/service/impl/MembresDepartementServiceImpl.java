package com.ecommerce.library.service.impl;


import com.ecommerce.library.model.Departement;
import com.ecommerce.library.model.MembreDepartement;

import com.ecommerce.library.model.Role;
import com.ecommerce.library.model.Users;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.repository.DepartementRepository;
import com.ecommerce.library.repository.MembresDepartementRepository;

import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.MembresDepartementService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembresDepartementServiceImpl implements MembresDepartementService {
    private final MembresDepartementRepository MembresDepartementRepository;

private final AdminRepository adminRepository;


    private final RoleRepository roleRepository;
private final DepartementRepository departementRepository;
    public MembreDepartement save(MembreDepartement membresDepartement) {
        // Create a new MembreDepartement object
        MembreDepartement membreDepartementToSave = new MembreDepartement();

        // Set non-referenced fields
        membreDepartementToSave.setLaboratoire(membresDepartement.getLaboratoire());
        membreDepartementToSave.setDomaineExpertise(membresDepartement.getDomaineExpertise());
        membreDepartementToSave.setFirstName(membresDepartement.getFirstName());
        membreDepartementToSave.setLastName(membresDepartement.getLastName());
        membreDepartementToSave.setUsername(membresDepartement.getUsername());





        // Save the MembreDepartement object
        MembreDepartement savedMembreDepartement = MembresDepartementRepository.save(membreDepartementToSave);

        // Print success or failure message to console
        System.out.println("Member saved successfully." + savedMembreDepartement);

        return savedMembreDepartement;
    }


    @Override
    public MembreDepartement update(MembreDepartement MembresDepartement) {
        MembreDepartement MembreDepartementUpdate = MembresDepartementRepository.getReferenceById(MembresDepartement.getId());
        MembreDepartementUpdate.setRole(MembresDepartement.getRole());
       MembreDepartementUpdate.setLaboratoire(MembresDepartement.getLaboratoire());
        return MembresDepartementRepository.save(MembreDepartementUpdate);
    }

    @Override
    public List<MembreDepartement> findALl() {
        Long id = 1L; // Identifiant spécifique pour la recherche, assurez-vous de le définir correctement
        System.out.println("La valeur du MembreDepartement avec l'identifiant 1 est : " + MembresDepartementRepository.findById(id));
        return MembresDepartementRepository.findAll();
    }





    @Override
    public Optional<MembreDepartement> findById(Long id) {
        return MembresDepartementRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<MembreDepartement> MembreDepartement = MembresDepartementRepository.findById(id);
        MembresDepartementRepository.deleteById(id);

    }

    @Override
    public List<MembreDepartement> getMembresByIdDepartement(Long idDepartement) {

        return MembresDepartementRepository.getMembreDepartementByDepartementId(idDepartement);
    }



}


