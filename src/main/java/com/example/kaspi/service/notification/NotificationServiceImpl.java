package com.example.kaspi.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendSms(UUID clientId, String message) {
        log.info("SMS to {}: {}", clientId, message);
    }
}

