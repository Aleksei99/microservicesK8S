package com.smuraha.notification;

import com.smuraha.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notification")
public class NotificationController {
    private final NotificationHistoryService notificationHistoryService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest){
        notificationHistoryService.sendNotification(notificationRequest);
        log.info("Notification has sent successfully for customerId {}",notificationRequest.getCustomerId());
    }

}
