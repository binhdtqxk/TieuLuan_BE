package com.essay.TieuLuan_BE.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String fullName;
    private int gender;
    private LocalDate birthday;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
