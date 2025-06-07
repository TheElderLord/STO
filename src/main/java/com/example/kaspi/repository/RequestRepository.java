package com.example.kaspi.repository;

import com.example.kaspi.enums.RequestStatusEnum;
import com.example.kaspi.domain.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<RequestModel, UUID> {
    List<RequestModel> findByClientId(UUID clientId);
    List<RequestModel> findByStatus(RequestStatusEnum status);
}

