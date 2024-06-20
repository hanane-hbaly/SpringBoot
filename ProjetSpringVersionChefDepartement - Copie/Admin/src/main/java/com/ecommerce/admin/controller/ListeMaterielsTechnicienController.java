package com.ecommerce.admin.controller;

import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.model.Panne;
import com.ecommerce.library.model.Ressource;
import com.ecommerce.library.repository.MembresDepartementRepository;
import com.ecommerce.library.repository.PanneRepository;
import com.ecommerce.library.repository.RessourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ListeMaterielsTechnicienController {

 private final MembresDepartementRepository membresDepartementRepository;
    private final PanneRepository panneRepository;
    private final RessourceRepository ressourceRepository;

    @GetMapping("/MaterielsTechnicien")
    public String categories(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<Panne> pannes=panneRepository.findAll();
        List<String> membreDepartements=new ArrayList<>();
        List<String> type=new ArrayList<>();
for(Panne panne:pannes)
{
    Long id = Long.valueOf(panne.getIdMembreDepartement());
    Optional<MembreDepartement> membreDepartement=  membresDepartementRepository.findById(id);
Optional<Ressource> ressource=ressourceRepository.findById(panne.getIdRessource());
    membreDepartements.add( membreDepartement.get().getUsername());
    type.add(ressource.get().getType());


}
        model.addAttribute("membreDepartements", membreDepartements);
        model.addAttribute("pannes", pannes);
        model.addAttribute("type", type);





        return "Technicien/ListeMateriels";
    }
}