package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.dto.mapper.NotificationDtoMapper;
import com.essay.TieuLuan_BE.entity.Notification;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetwitNotificationStrategy implements NotificationStrategy{
    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public NotificationType getNotificationType() {
        return NotificationType.RETWIT;
    }

    @Override
    public NotificationDto handleNotification(User sender, User recipient, Twit twit) {
        Notification notification = new Notification();
        notification.setType(NotificationType.RETWIT);
        notification.setSender(sender);
        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
        notification.setContent(fullName+" reposted your tweet");
        notification.setTwit(twit);
        notificationRepository.save(notification);
        return NotificationDtoMapper.toNotificationDto(notification);
    }
}
