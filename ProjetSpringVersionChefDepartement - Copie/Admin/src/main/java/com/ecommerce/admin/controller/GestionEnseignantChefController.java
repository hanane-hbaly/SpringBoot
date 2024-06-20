package com.ecommerce.admin.controller;

import com.ecommerce.library.model.*;
import com.ecommerce.library.repository.*;
import com.ecommerce.library.service.DepartementService;
import com.ecommerce.library.service.MembresDepartementService;
import com.ecommerce.library.service.OrdinateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GestionEnseignantChefController {

    private final MembresDepartementRepository membresDepartementRepository;
    private final AdminRepository adminRepository;
    private final MembresDepartementService MembreDepartementService;
    private final RoleRepository roleRepository;
    private final NotifEnseignantRepository notifEnseignantRepository;
    private final DepartementRepository departementRepository;


    @GetMapping("/EnseignantsChef")
    public String getAll(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();


        model.addAttribute("title", "EnseignantsChef");
        Users user =adminRepository.findByUsername(username);

        Long id = user.getId();
        Optional<MembreDepartement> membreDepartement=  membresDepartementRepository.findById(id);

        if(membreDepartement.isPresent()){
            Departement idDepar =membreDepartement.get().getDepartement();
            Long iddepp = idDepar.getId();

            List<MembreDepartement> Enseignants = membresDepartementRepository.getMembreDepartementByDepartement(idDepar);
            Enseignants.removeIf(Enseignant -> Enseignant.getId().equals(id));
            model.addAttribute("Enseignants",Enseignants );
            model.addAttribute("departement", iddepp);
        }
                return "Chef de Departement/GestionEnseignant";
    }
    @PostMapping(value = "/notifyMembers")
    public String save(@RequestParam("datefin") LocalDate datefin,
                       @RequestParam("Enseignants") List<Long> enseignantsIds,
                       @RequestParam("departement") Long dep,
                       Model model,RedirectAttributes redirectAttributes) {

        try {
            String message = new String("Vous devez ajouter vos demandes avans " + datefin);
            Departement departement11 = departementRepository.getById(dep);
            NotifEnseignant notif = new NotifEnseignant();
            notif.setDepartement(departement11);
            notif.setMessage(message);
            notif.setIsSeen(false);
            notifEnseignantRepository.save(notif);

            redirectAttributes.addFlashAttribute("success", "Enseignants bien Notifié");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "une Erreur est survenue");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "une Erreur est survenue");
        }
        return "redirect:/EnseignantsChef";
    }


}
