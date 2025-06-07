package com.example.kaspi.mapper;


import com.example.kaspi.dto.CreateRequestDto;
import com.example.kaspi.dto.RequestDto;
import com.example.kaspi.enums.RequestStatusEnum;
import com.example.kaspi.domain.RequestModel;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public RequestModel toEntity(CreateRequestDto dto) {
        return RequestModel.builder()
                .clientId(dto.getClientId())
                .carVin(dto.getCarVin())
                .description(dto.getDescription())
                .status(RequestStatusEnum.NEW)
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

