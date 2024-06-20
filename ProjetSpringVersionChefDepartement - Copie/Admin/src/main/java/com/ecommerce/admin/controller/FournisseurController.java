package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.TechnicienService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FournisseurController {

    private final com.ecommerce.library.service.OrdinateurService OrdinateurService;
    private final FournisseurRepository fournisseurRepository;
    private final AppelOffreRepository appelOffreRepository;
    private final NotifFournisseurRepository notifFournisseurRepository;
    private final RoleRepository roleRepository;
    private final AdminService adminService;
    private final OffreRepository offreRepository;
    @GetMapping("/fournisseur")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Fournisseurs");


        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        model.addAttribute("fournisseurs", fournisseurs);
        System.out.println("La valeur  est : " + fournisseurs);

        model.addAttribute("FournisseurNew", new Technicien());
//        }
        return "Responsable/fournisseur";
    }
    @RequestMapping(value = "/delete-fournisseur", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(String.valueOf(id));

            if (fournisseurOptional.isPresent()) {
                Fournisseur user = fournisseurOptional.get();
                user.setRole(null);
                // Supprimer l'utilisateur
                fournisseurRepository.delete(user);}

            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/fournisseur";
    }
    @PostMapping("/saveFournisseur")
    public String save(@ModelAttribute("FournisseurNew") Fournisseur fournisseur, Model model, RedirectAttributes redirectAttributes)  {



        Role technicienRole = roleRepository.findByName("fournisseur");
        System.out.println(technicienRole);
        fournisseur.setRole(technicienRole);

        // Save the technicien
        fournisseurRepository.save(fournisseur);

        redirectAttributes.addFlashAttribute("success", "Fournisseur added successfully!");
        return "redirect:/fournisseur";
    }
    @PostMapping("/reject-fournisseur")
    public String rejectFournisseur(@RequestParam(value = "fournisseur", required = false) String fournisseursSelectionne,
                                    Model model) {
        List<Long> fournisseurIds = Arrays.stream(fournisseursSelectionne.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for (Long fournisseurId : fournisseurIds) {
            Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findById(String.valueOf(fournisseurId));
            if (optionalFournisseur.isPresent()) {
                Fournisseur fournisseur = optionalFournisseur.get();
                fournisseur.setBlackList(true);
                fournisseurRepository.save(fournisseur);
            }

            List<Offre> offres = offreRepository.findOffreByIdFournisseur(String.valueOf(fournisseurId));
            for (Offre offre : offres) {
                offre.setIsRejected(true);
                offre.setIsWaiting(false);
            }
            offreRepository.saveAll(offres);
        }

        // Redirigez l'utilisateur vers la page des fournisseurs
        return "redirect:/fournisseur";
    }

    @PostMapping("/accept-fournisseurs")
    public String acceptFournisseurs(@RequestParam("fournisseurIds") String[] fournisseurIds,
                                     @RequestParam("addresses") String[] addresses,
                                     @RequestParam("managers") String[] managers) {
        // Traitement pour chaque fournisseur accepté
        System.out.println(Arrays.toString(addresses));
        for (int i = 0; i < fournisseurIds.length; i++) {
            String fournisseurId = fournisseurIds[i];
            String address = addresses[i];
            String manager = managers[i];

            // Récupérer la liste des notifications pour le fournisseur
            List<NotifFournisseur> notifFournisseurs = notifFournisseurRepository.findNotifFournisseurByIdFournisseur(String.valueOf(Long.valueOf(fournisseurId)));
            List<Offre> offres=offreRepository.findOffreByIdFournisseur(fournisseurId);
            for (Offre offre : offres) {
                offre.setIsAffected(true);
                offre.setIsWaiting(false);
                // Sauvegarder la notification mise à jour
                offreRepository.save(offre);
            }
            // Mettre à jour chaque notification pour ce fournisseur
            for (NotifFournisseur notifFournisseur : notifFournisseurs) {
                notifFournisseur.setIsAccepted(true);
                // Sauvegarder la notification mise à jour
                notifFournisseurRepository.save(notifFournisseur);
            }

            // Mettre à jour les détails du fournisseur
            Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(fournisseurId);
            if (fournisseurOptional.isPresent()) {
                Fournisseur fournisseur = fournisseurOptional.get();
                fournisseur.setAddresse(address);
                fournisseur.setGerant(manager);
                fournisseurRepository.save(fournisseur); // Correction ici, sauvegarde de l'objet fournisseur
            }





            // Faites ce que vous voulez avec les données (par exemple, enregistrer dans la base de données)
            System.out.println("Fournisseur ID: " + fournisseurId);
            System.out.println("Adresse: " + address);
            System.out.println("Gérant: " + manager);
            System.out.println("----------------------------");
        }

        // Rediriger vers une autre page après le traitement
        return "redirect:/fournisseur";
    }



}