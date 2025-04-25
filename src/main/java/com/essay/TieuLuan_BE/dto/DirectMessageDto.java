package com.essay.TieuLuan_BE.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DirectMessageDto {
    private Long id;
    private UserDto sender;
    private UserDto recipient;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
}
