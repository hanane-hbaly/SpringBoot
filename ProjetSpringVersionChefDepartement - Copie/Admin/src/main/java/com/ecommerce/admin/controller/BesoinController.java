package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.BesoinService;
import com.ecommerce.library.service.OrdinateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BesoinController {

    private final BesoinRepository besoinRepository;
    private final BesoinService besoinService;
    private final AdminRepository adminRepository;
    private final AppelOffreRepository appelOffreRepository;
    private final RoleRepository roleRepository;
    private final NotifFournisseurRepository notifFournisseurRepository;
    private final OffreRepository offreRepository;
    private final RessourceRepository ressourceRepository;
    private final FournisseurRepository fournisseurRepository;


    @GetMapping("/ResponsableListeBesoins")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Besoins");

        List<Besoin> besoins = besoinService.getBesoinsNotInAppelOffre();
        List<Ordinateur> ordinateurs = new ArrayList<>();

        for (Besoin besoin : besoins) {
            // Récupérer les ordinateurs associés à ce besoin
            Collection<Ordinateur> ordinateursAssocies = besoin.getOrdinateurs();
            ordinateurs.addAll(ordinateursAssocies);
        }
        System.out.println(ordinateurs);
        model.addAttribute("besoins", besoins);
        model.addAttribute("ordinateurs", ordinateurs);
        return "Responsable/besoins";
    }

    @PostMapping("/genererAppel")
    public String genererAppel(@RequestParam("dateFin") LocalDate dateFin,
                               @RequestParam(value = "besoins", required = false) String besoinsSelectionnes,
                               Model model) {
        // Convertir la chaîne de caractères des ID des besoins en une liste d'entiers
        List<Long> besoinsIds = Arrays.stream(besoinsSelectionnes.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Récupérer les besoins correspondants aux ID
        List<Besoin> besoins = new ArrayList<>();
        for (Long besoinId : besoinsIds) {
            Besoin besoin = besoinRepository.findById(besoinId).orElse(null);

            if (besoin != null) {
                besoin.setBesoinInAppelOffre(true);
                besoinRepository.save(besoin);
                besoins.add(besoin);
            }
        }

        // Créer un nouvel AppelOffre avec la date de publication, la date de fin et les besoins sélectionnés
        AppelOffre appelOffre = AppelOffre.builder()
                .datePub(new java.sql.Date(System.currentTimeMillis())) // Date d'aujourd'hui
                .dateFin(Date.valueOf(dateFin)) // Conversion de LocalDate en java.sql.Date
                .isAffected(false)
                .besoins(besoins)
                .build();

System.out.println(appelOffre.getBesoins());
        // Enregistrer l'AppelOffre dans la base de données
        appelOffreRepository.save(appelOffre);

        // Récupérer les identifiants des fournisseurs
        Role role = roleRepository.findByName("fournisseur");
        List<Fournisseur> fournisseur =fournisseurRepository.findAll();
        List<Long> fournisseurIds = new ArrayList<>();
        for(Fournisseur fournisseur1 : fournisseur){
            fournisseurIds.add(fournisseur1.getId());
        }

        // Notifier les fournisseurs
        LocalDate dateOffre = LocalDate.now();
        for (Long fournisseurId : fournisseurIds) {
            NotifFournisseur notifFournisseur = new NotifFournisseur();
            notifFournisseur.setIdFournisseur(fournisseurId.toString());
            notifFournisseur.setDateOffre(java.sql.Date.valueOf(dateOffre)); // Convertissez LocalDate en Date si nécessaire
            Offre offre=new Offre();
            offre.setIdFournisseur(String.valueOf(fournisseurId));
            offre.setIdAppelOffre(appelOffre.getId());
            offre.setDateDebut(Date.valueOf(dateOffre));
            offre.setDateFin(Date.valueOf(dateFin));
            offre.setIsWaiting(true);


            // Enregistrez la notification du fournisseur dans la base de données
            notifFournisseurRepository.save(notifFournisseur);
            offreRepository.save(offre);
        }

        return "redirect:/ResponsableListeBesoins";
    }








}