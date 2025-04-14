package com.essay.TieuLuan_BE.notificationService;

import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;

public interface NotificationService {

    void sendNotification(NotificationType type, UserDto sender, UserDto recipient, TwitDto twit);
}