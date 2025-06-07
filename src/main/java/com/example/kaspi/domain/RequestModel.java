package com.example.kaspi.domain;

import com.example.kaspi.enums.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID clientId;

    @Column(length = 17, nullable = false)
    private String carVin;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatusEnum status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(
            mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<StatusHistoryModel> history = new ArrayList<>();

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}

