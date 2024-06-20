package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Technicien;
import com.ecommerce.library.model.Users;

import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.TechnicienService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class TechnicienServiceImpl implements TechnicienService {

    private final TechnicienRepository technicienRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository userRepository;


    @Override
    public Technicien addTechnicien(Technicien technicien) {
        Users user = userRepository.findByUsername(technicien.getUsername());

        technicien.setPassword(passwordEncoder.encode(technicien.getPassword()));
//        technicien.setRole(List.of(authenticationService.saveRole("technicien")));
        return technicienRepository.save(technicien);
    }

    @Override
    public Technicien updateTechnicien(Technicien technicien) {
        return technicienRepository.save(technicien);
    }

    @Override
    public void deleteTechnicien(String id) {
        technicienRepository.deleteById(id);
    }

    @Override
    public List<Technicien> getAllTechniciens() {
        return technicienRepository.findAll();
    }

    @Override
    public Optional<Technicien> getTechnicienById(String id) {
        return technicienRepository.findById(id);
    }

}
