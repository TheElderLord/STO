package com.example.kaspi.dto;

import com.example.kaspi.enums.RequestStatusEnum;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangeStatusDto {
    private RequestStatusEnum newStatus;
    private String changedBy;
    private String reason;
}

