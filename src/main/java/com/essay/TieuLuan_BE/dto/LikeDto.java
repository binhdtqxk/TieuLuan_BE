package com.essay.TieuLuan_BE.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeDto {
    private Long id;
    private UserDto user;
    private TwitDto twit;
    private LocalDateTime createdAt;
}
