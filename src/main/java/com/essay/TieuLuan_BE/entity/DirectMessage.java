package com.essay.TieuLuan_BE.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "direct_messages")
@Data
public class DirectMessage {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User recipient;

    @Column(columnDefinition = "TEXT")
    private String content;


    private String imageUrl;

    private LocalDateTime createdAt;
}
