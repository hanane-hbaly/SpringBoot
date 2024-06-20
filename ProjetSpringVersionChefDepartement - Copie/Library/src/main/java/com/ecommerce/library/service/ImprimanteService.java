package com.ecommerce.library.service;

import com.ecommerce.library.model.Imprimante;

import java.util.List;

public interface ImprimanteService {

    Imprimante saveImprimante(Imprimante imprimante);
    List<Imprimante> getAllImprimantes();
    List<Imprimante> getImprimantesByMembreDepartement(String id);
    List<Imprimante> getImprimantesByDepartement(Long id);
    List<Imprimante> getImprimantesByFournisseur(String id);
    Imprimante getImprimante(Long id);
    Imprimante updateImprimante(Imprimante imprimante);
    void deleteImprimante(Long id);
    List<Imprimante> getImprimantesLivrees();
    List<Imprimante> getImprimantesDisponibles();

}
