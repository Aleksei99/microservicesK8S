package com.smuraha.notification;

import com.smuraha.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationHistoryService {
    private final NotificationHistoryRepository notificationHistoryRepo;

    public void sendNotification(NotificationRequest notificationRequest){
        notificationHistoryRepo.save(
                NotificationHistory.builder()
                        .customerId(notificationRequest.getCustomerId())
                        .notificationMessage(notificationRequest.getMessage())
                        .createdAt(LocalDateTime.now()).build()
        );
    }
}
