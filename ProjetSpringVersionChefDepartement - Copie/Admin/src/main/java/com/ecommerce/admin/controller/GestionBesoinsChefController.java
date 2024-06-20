package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.BesoinService;
import com.ecommerce.library.service.MembresDepartementService;
import com.ecommerce.library.service.OrdinateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GestionBesoinsChefController {

    private final OrdinateurService OrdinateurService;
    private final BesoinService besoinService;
    private final MembresDepartementRepository membresDepartementRepository;
    private final AdminRepository adminRepository;
    private final BesoinRepository besoinRepository;
    private final MembresDepartementService MembreDepartementService;
    private final RoleRepository roleRepository;
    private final NotifEnseignantRepository notifEnseignantRepository;
    private final DepartementRepository departementRepository;
    private final ImprimanteRepository imprimanteRepository;
    private final RessourceRepository ressourceRepository;

    @GetMapping("/ListeBesoinsChef")
    public String getAll(Model model, Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        model.addAttribute("title", "Gestion des Besoins");
        Users user = adminRepository.findByUsername(username);
        if (user != null) {
            Long id = user.getId();
            Optional<MembreDepartement> membreDepartement = membresDepartementRepository.findById(id);
            if (membreDepartement.isPresent()) {
                Departement departement = membreDepartement.get().getDepartement();
                Long idDepartement = departement.getId();
                List<Besoin> besoins = besoinService.getBesoinsDepartementNotInAppelOffreandNotAffected(idDepartement);
                List<Imprimante> imprimantes = new ArrayList<>();
                List<Ordinateur> ordinateurs = new ArrayList<>();
                List<Optional<MembreDepartement>> membresEnseignants = new ArrayList<>();
                for (Besoin besoin : besoins) {
                    for (Imprimante imprimante : besoin.getImprimantes()) {
                        imprimantes.add(imprimante);
                    }
                    for (Ordinateur ordinateur : besoin.getOrdinateurs()) {
                        ordinateurs.add(ordinateur);
                    }
                    Long idEnseignant = Long.valueOf(besoin.getIdMembreDepartement());
                    Optional<MembreDepartement> membreEnseignant = membresDepartementRepository.findById(idEnseignant);
                    if (membreEnseignant.isPresent()) {
                        membresEnseignants.add(membreEnseignant);
                    }
                }
                model.addAttribute("besoins", besoins);
                model.addAttribute("imprimantes", imprimantes);
                model.addAttribute("ordinateurs", ordinateurs);
                model.addAttribute("membresEnseignants", membresEnseignants);
            }
        }
        return "Chef de Departement/GestionBesoinsChef";
    }

    @PostMapping("/updateAllBesoinsSeenByChef")
    public String marquerTousBesoinsCommeVus(@RequestParam("Enseignants") List<Long> idBesoins,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {
        try {
            for (Long besoinId : idBesoins) {
                Optional<Besoin> besoinOptional = besoinRepository.findById(besoinId);
                besoinOptional.ifPresent(besoin -> {
                    besoin.setBesoinSeenByChef(true);
                    besoinRepository.save(besoin);
                });
            }
            redirectAttributes.addFlashAttribute("success", "Reponsable bien Notifié");
        }catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "une Erreur est survenue");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "une Erreur est survenue");
        }
        return "redirect:/ListeBesoinsChef";
    }

//    @GetMapping("/BesoinsNotif")
//    public String getAllNotif(Model model, Principal principal) {
//        System.out.println("Notif");
//        model.addAttribute("notifMessage", "Notif");
//        return "Chef de Departement/fragmentsChef";
//    }

    @GetMapping("/Chef de Departement/ChefDepartement")
    public String getAllNotif(Model model, Principal principal) {
        System.out.println("Notif");
        model.addAttribute("notifMessage", "Notif");
        return "Chef de Departement/ChefDepartement";
    }
}
