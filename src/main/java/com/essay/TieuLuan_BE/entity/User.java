package com.essay.TieuLuan_BE.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "birthDay")
    private LocalDate birthDay;
    @Column(name = "gender")
    private int gender;
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @Column(name = "updatedAt")
    private LocalDate updatedAt;
}
