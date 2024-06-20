package com.ecommerce.library.service;

import com.ecommerce.library.model.Technicien;

import java.util.List;
import java.util.Optional;

public interface TechnicienService {
    Technicien addTechnicien(Technicien technicien);
    Technicien updateTechnicien(Technicien technicien);
    void deleteTechnicien(String id);
    List<Technicien> getAllTechniciens();
    Optional<Technicien> getTechnicienById(String id);
}
