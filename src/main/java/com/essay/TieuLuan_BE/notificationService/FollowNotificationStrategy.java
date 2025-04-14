package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class FollowNotificationStrategy implements NotificationStrategy {
    @Override
    public NotificationType getNotificationType() {
        return NotificationType.FOLLOW;
    }

    @Override
    public NotificationDto handleNotification(UserDto sender, UserDto recipient, TwitDto twit) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType(NotificationType.FOLLOW);
        notificationDto.setSender(sender);
        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
        notificationDto.setContent(fullName+" just followed you");
        return notificationDto;
    }
}
