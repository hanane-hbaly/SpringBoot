package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Ressource;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.PanneService;
import com.ecommerce.library.service.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class PanneController {

    private final RessourceService ressourceService;
    private final AdminService adminService; // Injection du service AdminService

    private final PanneService panneService; // Injection du service AdminService

    @Autowired
    public PanneController(RessourceService ressourceService, AdminService adminService, PanneService panneService) {
        this.ressourceService = ressourceService;
        this.adminService = adminService;

        this.panneService = panneService;
    }

    @GetMapping("/signaler-panne")
    public String chargerCodesBarres(Model model) {
        // Récupérer le nom d'utilisateur de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Ajouter le nom d'utilisateur au modèle
        model.addAttribute("username", username);

        // Récupérer l'ID de l'utilisateur connecté
        Long userId = adminService.findByUsername(username).getId();
        model.addAttribute("userId", userId);

        List<Ressource> ressources = ressourceService.getAllRessources();
        model.addAttribute("ressources", ressources);
        return "Enseignant/GestionSignal"; // Nom de votre vue Thymeleaf pour afficher le formulaire de signalement de panne

    }
    @PostMapping("/signaler-panne")
    public String signalerPanne(@RequestParam("codeBarre") String codeBarre, Model model) {
        // Récupérer l'ID de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = adminService.findByUsername(username).getId();

        // Récupérer la date système
        Date dateApparition = new Date(System.currentTimeMillis());

        // Récupérer l'ID de la ressource à partir du code-barre
        Long idRessource = ressourceService.getRessourceIdByCodeBarre(codeBarre);

        // Insérer une nouvelle panne dans la base de données

        // Vous pouvez ajouter d'autres attributs de la panne selon vos besoins

        panneService.savePanneWithDetails(dateApparition,idRessource,userId.toString());

        // Rediriger vers une page de confirmation
        return "Enseignant/GestionSignal";
    }

}

