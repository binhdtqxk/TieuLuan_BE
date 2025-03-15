package com.essay.TieuLuan_BE.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

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
    @Column(name = "home")
    private String home;

}
