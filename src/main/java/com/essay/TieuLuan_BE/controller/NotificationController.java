package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.service.notificationService.NotificationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationQueryService queryService;

    @GetMapping
    public List<NotificationDto> listMyNotifications(@RequestHeader("Authorization") String jwt)throws UserException {
        return queryService.getNotificationsForCurrentUser(jwt);
    }
}
