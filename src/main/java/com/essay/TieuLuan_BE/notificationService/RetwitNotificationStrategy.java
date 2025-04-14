package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class RetwitNotificationStrategy implements NotificationStrategy{
    @Override
    public NotificationType getNotificationType() {
        return NotificationType.RETWIT;
    }

    @Override
    public NotificationDto handleNotification(UserDto sender, UserDto recipient, TwitDto twit) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType(NotificationType.RETWIT);
        notificationDto.setSender(sender);
        String fullName= sender.getFullName().replace(" ", "_")+ sender.getId();
        notificationDto.setContent(fullName+" reposted your tweet");
        notificationDto.setTwit(twit);
        return notificationDto;
    }
}
