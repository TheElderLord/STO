package com.example.kaspi.dto;



import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRequestDto {
    private UUID clientId;
    private String carVin;
    private String description;
}

