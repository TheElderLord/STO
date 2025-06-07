package com.example.kaspi.listener;

import com.example.kaspi.service.notification.NotificationService;
import com.example.kaspi.events.StatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusChangedListener {
    private final NotificationService notificationService;

    @KafkaListener(topics = "status-changes", groupId = "kaspi-group")
    public void onStatusChanged(StatusChangedEvent evt) {
        if (evt.getNewStatus().name().equals("DONE")) {
            String msg = "Ваша заявка " + evt.getRequestId() + " завершена.";
            notificationService.sendSms(evt.getClientId(), msg);
        }
    }
}

