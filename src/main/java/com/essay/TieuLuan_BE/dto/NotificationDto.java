package com.essay.TieuLuan_BE.dto;

import com.essay.TieuLuan_BE.notificationService.NotificationType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private NotificationType type;
    private UserDto sender;
    private UserDto recipient;
    private String content;
    private TwitDto twit;
}
