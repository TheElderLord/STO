package com.example.kaspi.service.users;

import com.example.kaspi.domain.UserModel;
import com.example.kaspi.dto.CreateUserDto;

public interface UserService {
    public String registerUser(CreateUserDto createUserDto);
}
