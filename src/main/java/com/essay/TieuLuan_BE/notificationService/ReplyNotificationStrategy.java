package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ReplyNotificationStrategy implements NotificationStrategy{
    @Override
    public NotificationType getNotificationType() {
        return NotificationType.REPLY;
    }

    @Override
    public NotificationDto handleNotification(UserDto sender, UserDto recipient, TwitDto twit) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType(NotificationType.REPLY);
        notificationDto.setSender(sender);
        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
        notificationDto.setContent(fullName+" replied your tweet");
        notificationDto.setTwit(twit);
        return notificationDto;
    }
}
