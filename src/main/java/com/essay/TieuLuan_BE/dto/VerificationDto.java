package com.essay.TieuLuan_BE.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class VerificationDto {
    private boolean status;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
