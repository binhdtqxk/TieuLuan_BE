package com.essay.TieuLuan_BE.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Verification {

    private boolean status=false;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;
}
