// src/main/java/com/example/kaspi/security/CustomUserDetailsService.java
package com.example.kaspi.security;

import com.example.kaspi.domain.UserModel;
import com.example.kaspi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        UserModel u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // теперь у нас ОДНА роль
        var authorities = List.of(
                new SimpleGrantedAuthority(u.getRole().name())
        );

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                u.isEnabled(),
                true, true, true,
                authorities
        );
    }
}
