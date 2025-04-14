package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.entity.Notification;

public class NotificationDtoMapper {
    public static NotificationDto toNotificationDto(Notification notification) {
        if (notification == null) {
            return null;
        }
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setType(notification.getType());
        dto.setSender(UserDtoMapper.toUserDto(notification.getSender()));
        dto.setRecipient(UserDtoMapper.toUserDto(notification.getRecipient()));
        dto.setContent(notification.getContent());
        return dto;
    }
}
