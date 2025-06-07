package com.example.kaspi.mapper;

import com.example.kaspi.domain.RequestModel;
import com.example.kaspi.domain.UserModel;
import com.example.kaspi.dto.CreateRequestDto;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.dto.RequestDto;
import com.example.kaspi.enums.RequestStatusEnum;
import org.springframework.security.core.userdetails.User;

public class UserMapper {
    public UserModel toEntity(CreateUserDto dto) {
        return UserModel.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public RequestDto toDto(RequestModel entity) {
        return RequestDto.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .carVin(entity.getCarVin())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
