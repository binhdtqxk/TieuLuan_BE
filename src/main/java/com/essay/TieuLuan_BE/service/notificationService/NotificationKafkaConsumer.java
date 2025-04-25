package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationKafkaConsumer {
    @Autowired
    NotificationRepository notificationRepository;
    
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationKafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consumeNotification(NotificationDto notificationDto) {

        messagingTemplate.convertAndSend("/topic/notifications", notificationDto);
//        System.out.println("Đã gửi thông báo qua WebSocket: " + notificationDto);
    }
}