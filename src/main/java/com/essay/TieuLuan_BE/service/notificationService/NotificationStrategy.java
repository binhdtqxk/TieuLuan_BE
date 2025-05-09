package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;

public interface NotificationStrategy {
    NotificationType getNotificationType();
    NotificationDto handleNotification(User sender, User recipient, Twit twit);
}
