package com.example.kaspi.service.request;

import com.example.kaspi.dto.ChangeStatusDto;
import com.example.kaspi.dto.CreateRequestDto;
import com.example.kaspi.dto.RequestDto;
import com.example.kaspi.enums.RequestStatusEnum;

import java.util.List;
import java.util.UUID;

public interface RequestService {
    RequestDto create(CreateRequestDto dto);
    List<RequestDto> getByClient(UUID clientId);
    List<RequestDto> getByStatus(RequestStatusEnum status);
    RequestDto changeStatus(UUID requestId, ChangeStatusDto dto);
}
