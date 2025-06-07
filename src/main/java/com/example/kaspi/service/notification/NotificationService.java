package com.example.kaspi.service.notification;


import java.util.UUID;

public interface NotificationService {
    void sendSms(UUID clientId, String message);
}

