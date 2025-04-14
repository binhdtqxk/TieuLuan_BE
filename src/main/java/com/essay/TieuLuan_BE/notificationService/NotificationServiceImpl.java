package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
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
                .collect(Collectors.toMap(NotificationStrategy::getNotificationType, s -> s)); //map notification type to each strategy
    }

    @Override
    public void sendNotification(NotificationType type, UserDto sender, UserDto recipient, TwitDto twit) {
        NotificationStrategy strategy = strategyMap.get(type);
        if(strategy == null) {
            throw new IllegalArgumentException("No strategy found for type " + type);
        }
        NotificationDto notificationDto = strategy.handleNotification(sender,recipient,twit);
        kafkaTemplate.send("notification", notificationDto);
    }
}
