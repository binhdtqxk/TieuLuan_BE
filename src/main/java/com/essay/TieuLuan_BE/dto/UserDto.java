package com.essay.TieuLuan_BE.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profilePicture;
    private String backgroundPhoto;
    private String overview;
    private String location;
    private String career;
    private LocalDateTime createdAt;
    private String home;
    private LocalDateTime updatedAt;
}
