package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Imprimante;
import com.ecommerce.library.model.Ordinateur;
import com.ecommerce.library.service.ImprimanteService;
import com.ecommerce.library.service.OrdinateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImprimanteController {

    private final ImprimanteService ImprimanteService;

    @GetMapping("/Imprimante")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Imprimantes");
        if (principal == null) {
            return "redirect:/login";
        } else {
            List<Imprimante> ImprimanteList = ImprimanteService.getAllImprimantes();
            model.addAttribute("imprimantes", ImprimanteList);
            System.out.println("ERYTIU"+ImprimanteList);
            return "Responsable/gestionImprimante";
        }
    }




}
