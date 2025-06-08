package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.mapper.NotificationDtoMapper;
import com.essay.TieuLuan_BE.entity.Notification;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FollowNotificationStrategy implements NotificationStrategy {

    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public NotificationType getNotificationType() {
        return NotificationType.FOLLOW;
    }

    @Override
    public NotificationDto handleNotification(User sender, User recipient, Twit twit) {
//        Notification notification = new Notification();
//        notification.setType(NotificationType.FOLLOW);
//        notification.setSender(sender);
//        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
//        notification.setContent(fullName+" just followed you");
//        notification.setRecipient(recipient);
//        notificationRepository.save(notification);
//        return NotificationDtoMapper.toNotificationDto(notification);
        Notification notification = new Notification();
        notification.setType(NotificationType.FOLLOW);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
        String content= fullName+" just followed you";
        notification.setContent(content);
        notification.setTwit(twit);
        notification.setCreatedAt(LocalDateTime.now());
        if (notificationRepository.existsBySenderAndRecipientAndContentAndTwit(sender,recipient,content,twit)) return null;
        notificationRepository.save(notification);
        return NotificationDtoMapper.toNotificationDto(notification);
    }
}
