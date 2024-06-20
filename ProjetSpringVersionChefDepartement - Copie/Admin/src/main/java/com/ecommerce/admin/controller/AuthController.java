package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.Fournisseur;
import com.ecommerce.library.model.Users;
import com.ecommerce.library.repository.FournisseurRepository;
import com.ecommerce.library.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.GrantedAuthority;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;

    private final BCryptPasswordEncoder passwordEncoder;
    private final FournisseurRepository fournisseurRepository;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Page Officiel");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }


        // Récupérer le nom du rôle de l'utilisateur actuel
        String roleName = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        // Rediriger en fonction du rôle
        switch (roleName) {
            case "Responsable":
                return "Responsable/Responsable";
            case "Chefdepartement":
//                return "Chef de Departement/ChefDepartement";
                return "Chef de Departement/ChefDepartement";
            case "Enseignant":
                return "Enseignant/enseignant";
            case "Fournisseur":
                return "Fournisseur/Fournisseur";
            case "Technicien":
                return "Technicien/Technicien";

            default:
                // Redirection par défaut, vous pouvez la personnaliser selon vos besoins
                return "redirect:/login";
        }
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new UserDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewFournisseur(@Valid @ModelAttribute("adminDto") UserDto adminDto,
                                    BindingResult result,
                                    Model model) {

        try {

            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String username = adminDto.getUsername();
            Users admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("admin not null");
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                System.out.println(adminDto);
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setUsername(adminDto.getUsername());
                fournisseur.setFirstName(adminDto.getFirstName());
                fournisseur.setLastName(adminDto.getLastName());
                String nomSociete=adminDto.getSociete();
                System.out.println(adminDto.getSociete());
                fournisseur.setNomSociete(nomSociete);
                fournisseur.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                fournisseur.setRepeatPassword(adminDto.getRepeatPassword());
                System.out.println(fournisseur);
                fournisseurRepository.save(fournisseur); // Assurez-vous d'ajouter un service pour enregistrer le fournisseur

                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";

    }
}
