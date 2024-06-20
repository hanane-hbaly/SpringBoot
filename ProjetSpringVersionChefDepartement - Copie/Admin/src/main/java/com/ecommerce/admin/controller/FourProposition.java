package com.ecommerce.admin.controller;


import com.ecommerce.library.model.Offre;
import com.ecommerce.library.model.Users;
import com.ecommerce.library.repository.OffreRepository;
import com.ecommerce.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class FourProposition {
    @Autowired
    private final OffreRepository offreRepository;
    private final AdminService adminService;

    @GetMapping("/Propos")
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        String userId = authentication.getName();
        model.addAttribute("title", "Mes Proposition");
        System.out.println("La userId  est : " + userId);
        return "Fournisseur/Proposition";
    }

    @GetMapping("/Proposition")
    public String getAllProposition(Model model) {
        // Récupérer l'authentification de l'utilisateur
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login"; // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        }
        String username = authentication.getName();
        Users user = adminService.findByUsername(username);
        // Récupérer l'ID de l'utilisateur
        Long userIdLong = Long.parseLong(String.valueOf(user.getId()));
        // Récupérer les offres du fournisseur à partir de l'ID de l'utilisateur
        List<Offre> offres = offreRepository.findOffreByIdFournisseur(String.valueOf(userIdLong));


        // Ajouter les offres à l'objet Model pour les rendre disponibles dans la vue
        model.addAttribute("offres", offres);
        model.addAttribute("title", "Mes Propositions");
        System.out.println("les offre "+offres);
        // Renvoyer le nom de la vue
        return "Fournisseur/Proposition";
    }


}


//@RequestMapping("/api/offres")