package com.smuraha.notification.rabbitmq;

import com.smuraha.clients.notification.NotificationRequest;
import com.smuraha.notification.NotificationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationHistoryService notificationHistoryService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void  consumer(NotificationRequest notificationRequest){
        log.info("Consumed {} from queue",notificationRequest);
        notificationHistoryService.sendNotification(notificationRequest);
    }
}
