package com.example.kaspi.domain;

import com.example.kaspi.enums.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;

@Entity
@Table(name = "status_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatusHistoryModel {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private RequestModel request;

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum oldStatus;

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum newStatus;

    @Column(nullable = false)
    private String changedBy;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false, updatable = false)
    private Instant changedAt;

    @PrePersist
    void onChange() {
        this.changedAt = Instant.now();
    }
}

