package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.AppelOffreRepository;
import com.ecommerce.library.repository.ResourceFournisseurRepository;
import com.ecommerce.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConsulterAppelOffre {


    private final AppelOffreRepository appelOffreRepository;
    @GetMapping("/ListeAppelOffreFour")
    public String ListeAppelOffre(Model model) {

        List<AppelOffre> appelOffres = appelOffreRepository.findAppelOffreByIsAffectedNull();
        List<List<Besoin>> besoinsParAppelOffre = new ArrayList<>();
        List<Ordinateur> ordinateurs = new ArrayList<>();
        List<Imprimante> imprimantes = new ArrayList<>();

        for (AppelOffre appelOffre : appelOffres) {
            Collection<Besoin> besoins = appelOffre.getBesoins();
            List<Besoin> besoinsDeCetAppelOffre = new ArrayList<>(besoins);
            besoinsParAppelOffre.add(besoinsDeCetAppelOffre);

            for (Besoin besoin : besoins) {
                // Ajouter tous les ordinateurs de ce besoin à la liste d'ordinateurs
                ordinateurs.addAll(besoin.getOrdinateurs());
                // Ajouter toutes les imprimantes de ce besoin à la liste d'imprimantes
                imprimantes.addAll(besoin.getImprimantes());
            }
        }

        model.addAttribute("besoinsParAppelOffre", besoinsParAppelOffre);
        model.addAttribute("appelOffres", appelOffres);
        model.addAttribute("ordinateurs", ordinateurs);
        model.addAttribute("imprimantes", imprimantes);



        return "Fournisseur/ConsulterAppelsDoffres";

    }

    @Autowired
    private ResourceFournisseurRepository resourceFournisseurRepository;
    private final AdminService adminService; // Injection du service AdminService
    @PostMapping("/enregistrerRessourceAvecFournisseur")
    public String enregistrerRessourceAvecFournisseur(@RequestParam String marque,
                                                      @RequestParam Double prix,
                                                      @RequestParam Long idRessource) {
        // Récupérer l'ID de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = adminService.findByUsername(username).getId();

        RessourceAvecFournisseur ressource = new RessourceAvecFournisseur();
        ressource.setMarque(marque);
        ressource.setPrix(prix);
        ressource.setIdRessource(idRessource);
        ressource.setId_fournisseur(userId);
        resourceFournisseurRepository.save(ressource);
        return "redirect:ListeAppelOffreFour";
    }

}
