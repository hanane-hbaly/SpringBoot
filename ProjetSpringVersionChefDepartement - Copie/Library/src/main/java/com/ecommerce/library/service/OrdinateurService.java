package com.ecommerce.library.service;


import com.ecommerce.library.model.Ordinateur;

import java.util.List;
import java.util.Optional;

public interface OrdinateurService {

    Ordinateur addOrdinateur(Ordinateur ordinateur);
    List<Ordinateur> getAllOrdinateurs();
    List<Ordinateur> getOrdinateursByMembreDepartement(String id);
    List<Ordinateur> getOrdinateursByDepartement(Long id);
    List<Ordinateur> getOrdinateursByFournisseur(String id);
    Optional<Ordinateur> getOrdinateur(Long id);
    Ordinateur updateOrdinateur(Ordinateur ordinateur);

    void deleteOrdinateur(Long id);
    List<Ordinateur> getOrdinateurLivrees();
    List<Ordinateur> getOrdinateurDisponibles();

}
