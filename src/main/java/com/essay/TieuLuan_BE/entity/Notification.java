package com.essay.TieuLuan_BE.entity;

import com.essay.TieuLuan_BE.service.notificationService.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    
    @ManyToOne
    private User sender;
    
    @ManyToOne
    private User recipient;

    private String content;

    @ManyToOne
    private Twit twit;

    private LocalDateTime createdAt;

}
