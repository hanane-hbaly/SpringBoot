package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Departement;
import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.model.Role;
import com.ecommerce.library.model.Users;
import com.ecommerce.library.repository.DepartementRepository;
import com.ecommerce.library.repository.MembresDepartementRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.MembresDepartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MembreDepartementController {

    private final MembresDepartementService MembreDepartementService;
    private final MembresDepartementRepository MembreSepartementRepository;
    private final DepartementRepository departementRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/GestionMembres")
    public String GestionMembres(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Membres Departement");
        List<MembreDepartement> MembreDepartement = MembreDepartementService.findALl();
        model.addAttribute("MembreDepartement", MembreDepartement);
        System.out.println("La valeur  MembreDepartement : " + MembreDepartement);

        model.addAttribute("MmbreDepartementNew", new MembreDepartement());
        return "Responsable/GestionMembres";
    }

    @PostMapping("/saveMembers")
    public String save(@ModelAttribute("MmbreDepartementNew") MembreDepartement MembreDepartement, Model model, RedirectAttributes redirectAttributes) {

        MembreDepartement.setPassword(passwordEncoder.encode(MembreDepartement.getPassword()));
        MembreSepartementRepository.save(MembreDepartement);

        return "redirect:/GestionMembres";
    }


    @RequestMapping(value = "/findByIdMembers", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<MembreDepartement> findById(Long id) {
        return MembreDepartementService.findById(id);
    }

    @GetMapping("/update-Members")
    public String update(MembreDepartement MembreDepartement, RedirectAttributes redirectAttributes) {
        try {
            MembreDepartementService.update(MembreDepartement);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/GestionMembres";
    }


    @RequestMapping(value = "/delete-Members", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            // Récupérer l'utilisateur à supprimer
            Optional<MembreDepartement> userOptional = MembreSepartementRepository.findById(id);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                user.setRole(null);
                // Supprimer l'utilisateur
                MembreSepartementRepository.delete((MembreDepartement) user);

                redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found!");
            }
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/GestionMembres";
    }

    @RequestMapping(value = "/AffecterChef", method = {RequestMethod.GET, RequestMethod.PUT})
    public String AffecterChef(Long id, RedirectAttributes redirectAttributes) {
        try {
            // Récupérer l'utilisateur à supprimer
            Optional<MembreDepartement> userOptional = MembreSepartementRepository.findById(id);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                Role role = roleRepository.findByName("Chefdepartement");
                System.out.println(role);
                MembreSepartementRepository.updateMemberRole(role,id);
                System.out.println("user"+user);


                redirectAttributes.addFlashAttribute("success", "Affected successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found!");
            }
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/GestionMembres";
    }

    @RequestMapping(value = "/AffecterEnseignant", method = {RequestMethod.GET, RequestMethod.PUT})
    public String AffecterEnseignant(Long id, RedirectAttributes redirectAttributes) {
        try {
            // Récupérer l'utilisateur à supprimer
            Optional<MembreDepartement> userOptional = MembreSepartementRepository.findById(id);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                Role role = roleRepository.findByName("Enseignant");
                System.out.println(role);
                MembreSepartementRepository.updateMemberRole(role,id);
                System.out.println("user"+user);


                redirectAttributes.addFlashAttribute("success", "Affected successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found!");
            }
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/GestionMembres";
    }




}