package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;

public interface NotificationService {

    void sendNotification(NotificationType type, User sender, User recipient, Twit twit);
}