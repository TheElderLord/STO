package com.example.kaspi.dto;

import com.example.kaspi.enums.RequestStatusEnum;
import lombok.*;
import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestDto {
    private UUID id;
    private UUID clientId;
    private String carVin;
    private String description;
    private RequestStatusEnum status;
    private Instant createdAt;
}

