package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.Users;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;


    @Override
    public Users save(UserDto adminDto) {
        Users admin = new Users();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setSociete(adminDto.getSociete());
        admin.setPassword(adminDto.getPassword());
        admin.setRole(roleRepository.findByName("Fournisseur"));
        return adminRepository.save(admin);
    }

    @Override
    public Users findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
