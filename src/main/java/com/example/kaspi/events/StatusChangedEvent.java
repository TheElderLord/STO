package com.example.kaspi.events;

import com.example.kaspi.enums.RequestStatusEnum;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StatusChangedEvent implements Serializable {
    private UUID requestId;
    private RequestStatusEnum newStatus;
    private UUID clientId;
}

