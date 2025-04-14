package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;

public interface NotificationStrategy {
    NotificationType getNotificationType();
    NotificationDto handleNotification(UserDto sender, UserDto recipient, TwitDto twit);
}
