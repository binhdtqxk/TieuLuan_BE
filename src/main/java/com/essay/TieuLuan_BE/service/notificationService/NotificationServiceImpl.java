package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private Map<NotificationType, NotificationStrategy> strategyMap;
    @Autowired
    private List<NotificationStrategy> strategies;

    @Autowired
    private KafkaTemplate<String, NotificationDto> kafkaTemplate;

    @PostConstruct
    public void init() {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(NotificationStrategy::getNotificationType, s -> s));
    }

    @Override
    public void sendNotification(NotificationType type, User sender, User recipient, Twit twit) {
        NotificationStrategy strategy = strategyMap.get(type);
        if(strategy == null) {
            throw new IllegalArgumentException("No strategy found for type " + type);
        }
        NotificationDto notificationDto = strategy.handleNotification(sender,recipient,twit);
        if(notificationDto!=null) kafkaTemplate.send("notification", notificationDto);
    }
}
