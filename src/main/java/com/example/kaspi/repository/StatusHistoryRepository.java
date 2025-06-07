package com.example.kaspi.repository;


import com.example.kaspi.domain.StatusHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusHistoryRepository extends JpaRepository<StatusHistoryModel, Long> {}

