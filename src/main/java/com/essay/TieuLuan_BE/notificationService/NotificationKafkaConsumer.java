package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationKafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationKafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void consumeNotification(NotificationDto notificationDto) {
        // Ở đây bạn có thể lưu vào DB, log, v.v...
        // Sau đó dùng WebSocket để đẩy thông báo đến client.
        messagingTemplate.convertAndSend("/topic/notifications", notificationDto);
        System.out.println("Đã gửi thông báo qua WebSocket: " + notificationDto);
    }
}