package com.example.kaspi.dto;


import com.example.kaspi.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserDto {
    private String username;
    private String password;
    private RoleEnum role;

}
