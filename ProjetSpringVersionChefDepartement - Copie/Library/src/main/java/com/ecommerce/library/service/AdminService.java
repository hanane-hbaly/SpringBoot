package com.ecommerce.library.service;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.Users;

public interface AdminService {
    Users save(UserDto adminDto);

    Users findByUsername(String username);

}
