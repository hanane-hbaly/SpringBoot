package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Panne;
import com.ecommerce.library.model.PanneAvecRessource;
import com.ecommerce.library.model.Ressource;
import com.ecommerce.library.repository.PanneAvecRessourceRepository;
import com.ecommerce.library.repository.PanneRepository;
import com.ecommerce.library.service.PanneService;
import com.ecommerce.library.service.RessourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ConstatsController {

    private final PanneService PanneService;
    private final PanneRepository PanneRepo;
    private final RessourceService RessourceService;
    private final PanneAvecRessourceRepository PanneAvecReso;
    @GetMapping("/AllConstats")

    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Constats");
        List<Panne> pannes = PanneService.getAllPannes();
        List<PanneAvecRessource> pannesAvecRessource = PanneService.getRessourcesForPanneId(pannes);

        // Récupérer les ressources pour chaque id de ressource de PanneAvecRessource
        List<Ressource> ressources = new ArrayList<>();
        for (PanneAvecRessource panneAvecRessource : pannesAvecRessource) {
            Long resourceId = panneAvecRessource.getIdRessource();
            Ressource resource = RessourceService.getRessourcesByIdAndIsdeleted(resourceId);

            ressources.add(resource);
        }
        List<PanneAvecRessource> PanneAvecRessource = new ArrayList<>();
        for (Ressource ressource : ressources) {
            if (ressource != null) {
                Long resourceId = ressource.getId();
                PanneAvecRessource PanneAvecres = PanneAvecReso.findPanneAvecRessourceByIdRessource(resourceId);

                PanneAvecRessource.add(PanneAvecres);} else {
                // Traitement à effectuer si ressource est null
                // Par exemple, vous pouvez logguer un message ou effectuer une action spécifique
                System.out.println("Une ressource est null");
            }
        }
        List<Optional<Panne>> Panne= new ArrayList<>();
        for (PanneAvecRessource PanneAvecRes : PanneAvecRessource) {
            Long PanneId = PanneAvecRes.getIdPanne();
            Optional<com.ecommerce.library.model.Panne> Pannes = PanneRepo.findById(PanneId);
            if(Pannes.isPresent())

                Panne.add(Pannes);
        }

        model.addAttribute("Pannes", Panne);
        model.addAttribute("PannesAvecRessource", PanneAvecRessource);
        model.addAttribute("Ressources", ressources);

        System.out.println("La valeur de Pannes est : " + Panne);
        System.out.println("La valeur de PannesAvecRessource est : " + PanneAvecRessource);
        System.out.println("La valeur de Ressources est : " + ressources);

        return "Responsable/constats";
    }
    @GetMapping(value = "/update-constat")
    public String updateConstat(@RequestParam("id") Long ressourceId, Model model) {
        // Recherche de la ressource par son identifiant
        Optional<Ressource> optionalRessource = RessourceService.getRessourcesById(ressourceId);

        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();

            // Mettre à jour le champ isDeleted à true
            ressource.setIsDeleted(true);

            // Enregistrer la mise à jour de la ressource
            RessourceService.addRessource(ressource);

            // Ajouter un attribut de modèle pour le message de succès
            model.addAttribute("success", "La ressource a été supprimée avec succès.");

            // Rediriger vers la même page
            return "redirect:/AllConstats";
        } else {
            // Gérer le cas où la ressource n'est pas trouvée
            throw new RuntimeException("Ressource non trouvée pour l'ID: " + ressourceId);
        }
    }




//    @RequestMapping(value = "/update-constat", method = {RequestMethod.PUT, RequestMethod.GET})
//    public String acceptOrder(Long id, RedirectAttributes attributes, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        } else {
//
//            attributes.addFlashAttribute("success", "Order Accepted");
//            return "Responsable/constats";
//        }
}