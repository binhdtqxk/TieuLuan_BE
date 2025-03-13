package com.essay.TieuLuan_BE.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "user_name")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "background_photo")
    private String backgroundPhoto;
    @Column(name = "overview")
    private String overview;
    @Column(name = "location")
    private String location;
    @Column(name = "career")
    private String career;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "home")
    private String home;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
