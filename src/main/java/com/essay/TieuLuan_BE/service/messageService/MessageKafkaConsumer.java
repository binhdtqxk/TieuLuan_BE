package com.essay.TieuLuan_BE.service.messageService;

import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageKafkaConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public MessageKafkaConsumer(SimpMessagingTemplate template) {
        this.messagingTemplate = template;
    }
    @KafkaListener(topics = "message", groupId = "message-group")
    public void consume(DirectMessageDto dto){
        messagingTemplate.convertAndSendToUser(
                dto.getRecipient().getId().toString(),
                "/queue/message",
                dto
        );
    }
}
