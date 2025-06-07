package com.example.kaspi.service.users;

import com.example.kaspi.domain.UserModel;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.repository.UserRepository;
import com.example.kaspi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    @Override
    public String registerUser(CreateUserDto createUserDto) {
        UserModel userModel = new UserModel();
        userModel.setUsername(createUserDto.getUsername());
        userModel.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userModel.setRole(createUserDto.getRole());
        userRepository.save(userModel);

        String token = tokenProvider.createToken(createUserDto.getUsername(),createUserDto.getRole());
        return token;
    }
}
