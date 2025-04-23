package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.Notification;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsBySenderAndRecipientAndContentAndTwit(
            User sender,
            User recipient,
            String content,
            Twit twit
    );
    boolean existsBySenderAndRecipientAndContentAndTwitIsNull(
            User sender,
            User recipient,
            String content
    );
    List<Notification> findByRecipientOrderByCreatedAtDesc(User recipient);
}
