

package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DemandeController {

    private final DepartementService departementService;
    private final BesoinService besoinService;
    private final AdminService adminService;
    private final OrdinateurService ordinateurService;
    private  final ImprimanteService imprimanteService;
    private final RessourceService ressourceService;
    private final BesoinRepository besoinRepository;
    private final OrdinateurRepository ordinateurRepository;
    private final DepartementRepository departementRepository;
    private final MembresDepartementRepository membreDepartementRepository;
    private final ImprimanteRepository imprimanteRepository;

    @GetMapping("/Demande")
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        String userId = authentication.getName();
        model.addAttribute("title", "AjouterBesoin");
//       model.addAttribute("ordinateur", new Ordinateur());
        System.out.println("La userId  est : " + userId);
        return "Enseignant/GestionDemande";
    }
//    Model model, RedirectAttributes redirectAttributes,
//@ModelAttribute("ordinateur")

    @PostMapping("/save-Besoin")
    public String save(@Valid  Besoin besoin,
                       String cpu, String ram, String disqueDur, String ecran ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Récupérer le nom d'utilisateur de l'utilisateur actuel
            Users user = adminService.findByUsername(username);
            System.out.println("La raam" + ram);
            System.out.println("La ecran" + ecran);
            System.out.println("La disqueDur" + disqueDur);
            System.out.println("La cpu" + cpu);

            // Créer un nouvel objet Ordinateur et le configurer
            Ordinateur ordinateur1 = new Ordinateur();
            ordinateur1.setEcran(ecran);
            ordinateur1.setRam(Long.parseLong(ram));
            ordinateur1.setCpu(cpu);
            ordinateur1.setDisqueDur(disqueDur);
            // Récupérer l'ID de l'utilisateur
            Long userIdLong = Long.parseLong(String.valueOf(user.getId()));
            // Rechercher le membre du département avec l'ID de l'utilisateur
            Optional<MembreDepartement> membreOptional = membreDepartementRepository.findById(userIdLong);
            if (membreOptional.isPresent()) {
                MembreDepartement membre = membreOptional.get();
                ordinateur1.setIdMembreDepartement(String.valueOf(membre.getId()));
                ordinateur1.setIdDepartement(membre.getDepartement().getId());
                besoin.setIdMembreDepartement(String.valueOf(membre.getId()));
                besoin.setIdDepartement(membre.getDepartement().getId());
                besoin.setDateDemande(new Date());
                besoin.getOrdinateurs().add(ordinateur1);
                besoinRepository.save(besoin);
                ordinateurRepository.save(ordinateur1);
            }
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace();
        }
        return "redirect:/Demande";// Rediriger vers une page appropriée
    }


    @PostMapping("/save-imp")
    public String saveImprimente(@Valid @ModelAttribute("ordinateur") Besoin besoin
                   ,String resolution,String vitesse) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Récupérer le nom d'utilisateur de l'utilisateur actuel
            Users user = adminService.findByUsername(username);
            System.out.println(resolution);

            Imprimante imprimante1=new Imprimante();
            imprimante1.setResolution(resolution);
            imprimante1.setVitesseImpression(vitesse);

            // Récupérer l'ID de l'utilisateur
            Long userIdLong = Long.parseLong(String.valueOf(user.getId()));

            // Rechercher le membre du département avec l'ID de l'utilisateur
            Optional<MembreDepartement> membreOptional = membreDepartementRepository.findById(userIdLong);
            if (membreOptional.isPresent()) {
                MembreDepartement membre = membreOptional.get();
                imprimante1.setIdDepartement(membre.getDepartement().getId());
                imprimante1.setIdMembreDepartement(String.valueOf(membre.getId()));
                besoin.setIdMembreDepartement(String.valueOf(membre.getId()));
                besoin.setIdDepartement(membre.getDepartement().getId());
                besoin.setDateDemande(new Date());
                besoin.getImprimantes().add(imprimante1);
                besoinRepository.save(besoin);
                imprimanteRepository.save(imprimante1);
            }
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace();
        }
        return "redirect:/Demande";// Rediriger vers une page appropriée
    }




}

