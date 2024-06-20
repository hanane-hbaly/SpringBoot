package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Ordinateur;
import com.ecommerce.library.service.OrdinateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrdinateurController {

    private final OrdinateurService OrdinateurService;

    @GetMapping("/Ordinateur")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Ordinateurs");
        if (principal == null) {
            return "redirect:/login";
        } else {
            List<Ordinateur> OrdinateurList = OrdinateurService.getAllOrdinateurs();
            model.addAttribute("Ordinateurs", OrdinateurList);
            System.out.println("ERYTIU"+OrdinateurList);
            return "Responsable/gestionOrdinateur";
        }
    }




}
