//package com.example.kaspi.config;
//
//import com.example.kaspi.domain.UserModel;
//import com.example.kaspi.enums.RoleEnum;
//import com.example.kaspi.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Set;
//import java.util.UUID;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//    private final UserRepository userRepo;
//    private final PasswordEncoder encoder;
//
//    @Override
//    public void run(String... args) {
//        if (userRepo.findByUsername("admin").isEmpty()) {
//            userRepo.save(UserModel.builder()
//                    .id(UUID.randomUUID())
//                    .username("admin")
//                    .password(encoder.encode("adminPass"))
//                    .enabled(true)
//                    .roles(Set.of(RoleEnum.ROLE_ADMIN))
//                    .build()
//            );
//        }
//    }
//}
//
