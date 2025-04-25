package com.essay.TieuLuan_BE.service.notificationService;

import com.essay.TieuLuan_BE.dto.NotificationDto;
import com.essay.TieuLuan_BE.dto.mapper.NotificationDtoMapper;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.repository.NotificationRepository;
import com.essay.TieuLuan_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationQueryService {
    @Autowired
    private NotificationRepository repo;
    @Autowired private UserService userService;

    public List<NotificationDto> getNotificationsForCurrentUser(String jwt) throws UserException {
        User me = userService.findUserProfileByJwt(jwt);
        return repo.findByRecipientOrderByCreatedAtDesc(me).stream()
                   .map(NotificationDtoMapper::toNotificationDto)
                   .collect(Collectors.toList());
    }
}
