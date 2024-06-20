package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Departement;
import com.ecommerce.library.model.MembreDepartement;
import com.ecommerce.library.model.Role;
import com.ecommerce.library.model.Technicien;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.repository.TechnicienRepository;
import com.ecommerce.library.service.AdminService;
import com.ecommerce.library.service.DepartementService;
import com.ecommerce.library.service.OrdinateurService;
import com.ecommerce.library.service.TechnicienService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ThecnicienController {

    private final com.ecommerce.library.service.OrdinateurService OrdinateurService;
    private final TechnicienService technicienService;
    private final TechnicienRepository technicienRepository;
    private final RoleRepository roleRepository;
    private final AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/techniciens")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("title", "Techniciens");


        List<Technicien> techniciens = technicienService.getAllTechniciens();
        model.addAttribute("techniciens", techniciens);
        System.out.println("La valeur  est : " + techniciens);

        model.addAttribute("TechnicienNew", new Technicien());
//        }
        return "Responsable/techniciens";
    }
    @RequestMapping(value = "/delete-technicien", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            technicienRepository.deleteById(String.valueOf(id));
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/techniciens";
    }
    @PostMapping("/saveTechnicien")
    public String save(@ModelAttribute("TechnicienNew") Technicien technicien, Model model, RedirectAttributes redirectAttributes)  {

        technicien.setPassword(passwordEncoder.encode(technicien.getPassword()));
        // Set the role for the technicien (assuming it's predefined)
        Role technicienRole = roleRepository.findByName("technicien");
        technicien.setRole(technicienRole);

        // Save the technicien
        technicienService.addTechnicien(technicien);

        redirectAttributes.addFlashAttribute("success", "Technicien added successfully!");
        return "redirect:/techniciens";
    }
}