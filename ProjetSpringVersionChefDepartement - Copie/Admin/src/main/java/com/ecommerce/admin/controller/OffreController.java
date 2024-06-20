package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OffreController {
    private final OffreRepository offreRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ResourceFournisseurRepository resourceFournisseurRepository;
    private final BesoinRepository besoinRepository;
    private final OrdinateurRepository ordinateurRepository;
    private final ImprimanteRepository imprimanteRepository;
    private final RessourceRepository ressourceRepository;
    private final AppelOffreRepository appelOffreRepository;
    @GetMapping("/offres")
    public String showOrders(Model model) {
        model.addAttribute("title", "Offres");
        // Récupérer toutes les offres
        List<Offre> offres = offreRepository.findAll();

        // Liste pour stocker les détails des fournisseurs
        List<Fournisseur> fournisseurDetailsList = new ArrayList<>();

        // Parcourir chaque offre pour récupérer les détails des fournisseurs
        for (Offre offre : offres) {
            // Récupérer l'identifiant du fournisseur de l'offre
            Long fournisseurId = Long.valueOf(offre.getIdFournisseur());

            // Rechercher le fournisseur dans la table Fournisseur
            Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(String.valueOf(fournisseurId));

            // Vérifier si le fournisseur existe
            if (fournisseurOptional.isPresent()) {
                Fournisseur fournisseur = fournisseurOptional.get();
                // Créer un objet FournisseurDetails pour stocker les détails du fournisseur
                Fournisseur fournisseurDetails = new Fournisseur();
                fournisseurDetails.setFirstName(fournisseur.getFirstName());
                fournisseurDetails.setLastName(fournisseur.getLastName());
                // Ajouter les détails du fournisseur à la liste
                fournisseurDetailsList.add(fournisseurDetails);
            }
        }

        // Ajouter les offres et les détails des fournisseurs au modèle
        model.addAttribute("offres", offres);
        model.addAttribute("fournisseurDetailsList", fournisseurDetailsList);

        return "Responsable/Offre";
    }

    @PostMapping("/traiter-ressources")
    public String traiterRessources(@RequestParam String offreId, @RequestParam String codeBarre) {
        try {
            // Récupérer l'appel d'offre
            System.out.println(offreId);
            System.out.println(codeBarre);
            Optional<Offre> offre = offreRepository.findById(Long.valueOf(offreId));
            System.out.println(offre);
            if (!offre.isPresent()) {
                return "redirect:/offres";
            }
            Offre offre1 = offre.get();
            String idfournisseur=offre1.getIdFournisseur();
            Long idAppelOffre=offre1.getIdAppelOffre();
            Optional<AppelOffre> appelOffre=appelOffreRepository.findAppelOffreById(idAppelOffre);
            if (!appelOffre.isPresent())
                System.out.println("doesn't exist");
            AppelOffre appelOffre1=appelOffre.get();

            // Récupérer les besoins associés à l'appel d'offre
            Collection<Besoin> besoins = appelOffre1.getBesoins();

            // Démarrez une transaction pour garantir l'intégrité des données


            // Parcourir les besoins et récupérer les ordinateurs et imprimantes associés
            for (Besoin besoin : besoins) {
                Collection<Ordinateur> ordinateurs = besoin.getOrdinateurs();


                // Pour chaque ordinateur, ajouter le code-barres à la ressource
                if(!ordinateurs.isEmpty()){
                    for (Ordinateur ordinateur : ordinateurs) {
                        ordinateur.setCodeBarre(codeBarre);
                        ordinateur.setIdFournisseur(idfournisseur);
                        ressourceRepository.save(ordinateur);
                        RessourceAvecFournisseur ressourceAvecFournisseur=new RessourceAvecFournisseur();
                        ressourceAvecFournisseur.setIdRessource(ordinateur.getId());
                        ressourceAvecFournisseur.setPrix(ordinateur.getPrix());
                        ressourceAvecFournisseur.setMarque(ordinateur.getMarque());
                        resourceFournisseurRepository.save(ressourceAvecFournisseur);
                    }}
                else {Collection<Imprimante> imprimantes = besoin.getImprimantes();

                    // Pour chaque imprimante, ajouter le code-barres à la ressource
                    if(!imprimantes.isEmpty()){
                        for (Imprimante imprimante : imprimantes) {
                            imprimante.setCodeBarre(codeBarre);
                            imprimante.setIdFournisseur(idfournisseur);
                            ressourceRepository.save(imprimante);
                            RessourceAvecFournisseur ressourceAvecFournisseur=new RessourceAvecFournisseur();
                            ressourceAvecFournisseur.setIdRessource(imprimante.getId());
                            ressourceAvecFournisseur.setPrix(imprimante.getPrix());
                            ressourceAvecFournisseur.setMarque(imprimante.getMarque());
                            resourceFournisseurRepository.save(ressourceAvecFournisseur);
                        }  }                  }
            }


            // Valider et valider les changements de la transaction

            return "redirect:/offres";
        } catch (DataAccessException e) {
            // Gérer les erreurs spécifiques à l'accès aux données
            e.printStackTrace();
            return "redirect:/offres";
        } catch (Exception e) {
            // Gérer les autres exceptions générales
            e.printStackTrace();
            return "redirect:/offres";
        }
    }


}