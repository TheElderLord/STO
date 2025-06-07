package com.example.kaspi.controller;

import com.example.kaspi.dto.ChangeStatusDto;
import com.example.kaspi.dto.CreateRequestDto;
import com.example.kaspi.dto.RequestDto;
import com.example.kaspi.enums.RequestStatusEnum;
import com.example.kaspi.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService service;

    @PostMapping
    public ResponseEntity<RequestDto> create(@RequestBody CreateRequestDto dto) {
        RequestDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<RequestDto> list(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) RequestStatusEnum status
    ) {
        if (clientId != null) return service.getByClient(clientId);
        if (status != null)   return service.getByStatus(status);
        throw new IllegalArgumentException("Specify clientId or status");
    }

    @PostMapping("/{id}/status")
    public RequestDto changeStatus(
            @PathVariable UUID id,
            @RequestBody ChangeStatusDto dto
    ) {
        return service.changeStatus(id, dto);
    }
}

