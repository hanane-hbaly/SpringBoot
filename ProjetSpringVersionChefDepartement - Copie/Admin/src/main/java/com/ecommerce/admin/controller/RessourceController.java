package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.BesoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RessourceController {


    private final RessourceRepository ressourceRepository;
    private final OrdinateurRepository ordinateurRepository;
    private final ImprimanteRepository imprimanteRepository;
    private final DepartementRepository departementRepository;
    private final MembresDepartementRepository membresDepartementRepository;
    private final FournisseurRepository fournisseurRepository;
    private final BesoinRepository besoinRepository;
    private final AppelOffreRepository appelOffreRepository;
    private final OffreRepository offreRepository;

    @GetMapping("/ListeRessource")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Ressources");

        List<Ordinateur> ordinateurs = ordinateurRepository.findAll();
        List<Imprimante> imprimantes = imprimanteRepository.findAll();

        // Liste pour stocker les noms de département et de membre de département correspondants
        List<String> nomDepartements = new ArrayList<>();
        List<String> nomMembresDepartement = new ArrayList<>();
        List<String> nomDepartementsImpr = new ArrayList<>();
        List<String> nomMembresDepartementImpr = new ArrayList<>();
        List<String> Fournisseur=new ArrayList<>();
        List<String> FournisseuImprr=new ArrayList<>();
        // Récupérer les noms de département et de membre de département pour chaque ordinateur
        for (Ordinateur ordinateur : ordinateurs) {
            Departement deaprtement = departementRepository.findDepartementById(ordinateur.getIdDepartement());
            String nomDepartement = (deaprtement != null) ? deaprtement.getNomDepartement() : "";
            MembreDepartement membreDepartement = membresDepartementRepository.findMembreDepartementById(Long.valueOf(ordinateur.getIdMembreDepartement()));
            String firstName = (membreDepartement != null) ? membreDepartement.getFirstName() : "";

            com.ecommerce.library.model.Fournisseur fournisseur=fournisseurRepository.findFournisseurById(Long.valueOf(ordinateur.getIdFournisseur()));
            String fournisseurname=fournisseur.getUsername();
            Fournisseur.add(fournisseurname);
            nomDepartements.add(nomDepartement);
            nomMembresDepartement.add(firstName);

        }

        // Récupérer les noms de département et de membre de département pour chaque imprimante
        for (Imprimante imprimante : imprimantes) {
            Departement deaprtement = departementRepository.findDepartementById(imprimante.getIdDepartement());
            String nomDepartement = (deaprtement != null) ? deaprtement.getNomDepartement() : "";
            MembreDepartement membreDepartement = membresDepartementRepository.findMembreDepartementById(Long.valueOf(imprimante.getIdMembreDepartement()));
            String firstName = (membreDepartement != null) ? membreDepartement.getFirstName() : "";

            com.ecommerce.library.model.Fournisseur fournisseur=fournisseurRepository.findFournisseurById(Long.valueOf(imprimante.getIdFournisseur()));
            String fournisseurname=fournisseur.getUsername();
            FournisseuImprr.add(fournisseurname);
            nomDepartementsImpr.add(nomDepartement);
            nomMembresDepartementImpr.add(firstName);

        }
        List<Departement> departements = departementRepository.findAll();
        List<MembreDepartement> membresDepartement = membresDepartementRepository.findAll();

        model.addAttribute("departements", departements);
        model.addAttribute("membresDepartement", membresDepartement);


        System.out.println(imprimantes);
        model.addAttribute("ordinateurs", ordinateurs);
        model.addAttribute("imprimantes", imprimantes);
        model.addAttribute("nomDepartements", nomDepartements);
        model.addAttribute("nomMembresDepartement", nomMembresDepartement);
        model.addAttribute("nomDepartementsImpr", nomDepartementsImpr);
        model.addAttribute("nomMembresDepartementImpr", nomMembresDepartementImpr);

        model.addAttribute("Fournisseur", Fournisseur);
        model.addAttribute("FournisseuImprr", FournisseuImprr);

        return "Responsable/Ressources";
    }

    @PostMapping("/affecterRessource")
    public String affecterRessource(@RequestParam String departementSelect,
                                    @RequestParam String membreSelect,
                                    @RequestParam String ordinateurId ) {

        // Convertir les IDs en Long
        Long departementId = Long.valueOf(departementSelect);
        Long membreId = Long.valueOf(membreSelect);


        // Récupérer les entités depuis la base de données
        Optional<Departement> departementOptional = departementRepository.findById(departementId);
        Optional<MembreDepartement> membreOptional = membresDepartementRepository.findById(membreId);
        Optional<Ordinateur> ordinateurOptional = ordinateurRepository.findById(Long.valueOf(ordinateurId));

        if (departementOptional.isPresent() && membreOptional.isPresent() && ordinateurOptional.isPresent()) {
            Departement departement = departementOptional.get();
            MembreDepartement membre = membreOptional.get();
            Ordinateur ordinateur = ordinateurOptional.get();

            // 1. Mettre à jour l'ordinateur
            ordinateur.setIsAffected(true);
            ordinateur.setIdDepartement(departement.getId());
            ordinateur.setIdMembreDepartement(String.valueOf(membre.getId()));
            ordinateurRepository.save(ordinateur);



            // 3. Récupérer le besoin correspondant à l'ordinateur et le marquer comme affecté
            List<Besoin> besoins = besoinRepository.findAll();
            for (Besoin besoin : besoins) {
                // Vérifier si le besoin correspond à l'ordinateur donné
                if (besoin.getOrdinateurs().contains(ordinateur)) {
                    // Mettre à jour le besoin
                    besoin.setAffected(true);
                    besoin.setDateAffectation(new Date());
                    besoinRepository.save(besoin);
                }
            }



        }

        return "redirect:/ListeRessource"; // Redirigez vers la page d'accueil ou une autre page appropriée
    }
    @PostMapping("/affecterImpr")
    public String affecterImpr(@RequestParam String departementSelect1,
                               @RequestParam String membreSelect1,
                               @RequestParam String imprimanteId ) {

        // Convertir les IDs en Long
        Long departementId1 = Long.valueOf(departementSelect1);
        Long membreId1 = Long.valueOf(membreSelect1);


        // Récupérer les entités depuis la base de données
        Optional<Departement> departementOptional = departementRepository.findById(departementId1);
        Optional<MembreDepartement> membreOptional = membresDepartementRepository.findById(membreId1);
        Optional<Ordinateur> ordinateurOptional = ordinateurRepository.findById(Long.valueOf(imprimanteId));

        if (departementOptional.isPresent() && membreOptional.isPresent() && ordinateurOptional.isPresent()) {
            Departement departement = departementOptional.get();
            MembreDepartement membre = membreOptional.get();
            Ordinateur ordinateur = ordinateurOptional.get();

            // 1. Mettre à jour l'ordinateur
            ordinateur.setIsAffected(true);
            ordinateur.setIdDepartement(departement.getId());
            ordinateur.setIdMembreDepartement(String.valueOf(membre.getId()));
            ordinateurRepository.save(ordinateur);



            // 3. Récupérer le besoin correspondant à l'ordinateur et le marquer comme affecté
            List<Besoin> besoins = besoinRepository.findAll();
            for (Besoin besoin : besoins) {
                // Vérifier si le besoin correspond à l'ordinateur donné
                if (besoin.getOrdinateurs().contains(ordinateur)) {
                    // Mettre à jour le besoin
                    besoin.setAffected(true);
                    besoin.setDateAffectation(new Date());
                    besoinRepository.save(besoin);
                }
            }



        }

        return "redirect:/ListeRessource"; // Redirigez vers la page d'accueil ou une autre page appropriée
    }
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Ressource> ordinateur=ressourceRepository.findById(id);
            System.out.println(ordinateur);
            if(!ordinateur.isPresent())
            {System.out.println("error");
                ressourceRepository.deleteById(id);
            }
            List<Besoin> besoins = besoinRepository.findAll();
            System.out.println(besoins);
            for (Besoin besoin : besoins) {
                Collection<Ordinateur> ordinateurs = besoin.getOrdinateurs();
                Iterator<Ordinateur> iterator = ordinateurs.iterator();
                while (iterator.hasNext()) {
                    Ordinateur ordinateur1 = iterator.next();
                    if (ordinateur1.getId().equals(id)) {
                        iterator.remove(); // Supprimer l'élément de la collection
                    }
                }
                besoinRepository.save(besoin); // Mettre à jour l'entité Besoin

            }

            ressourceRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/ListeRessource";
    }

    @RequestMapping(value = "/deleteImpr", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deleteImpr(Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Ressource> ordinateur=ressourceRepository.findById(id);
            System.out.println(ordinateur);
            if(!ordinateur.isPresent())
            {System.out.println("error");
                ressourceRepository.deleteById(id);
            }
            List<Besoin> besoins = besoinRepository.findAll();
            System.out.println(besoins);
            for (Besoin besoin : besoins) {
                Collection<Imprimante> imprimantes = besoin.getImprimantes();
                Iterator<Imprimante> iterator = imprimantes.iterator();
                while (iterator.hasNext()) {
                    Imprimante imprimante = iterator.next();
                    if (imprimante.getId().equals(id)) {
                        iterator.remove(); // Supprimer l'élément de la collection
                    }
                }
                besoinRepository.save(besoin); // Mettre à jour l'entité Besoin

            }

            ressourceRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/ListeRessource";
    }







}