package com.essay.TieuLuan_BE.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String image;
    private String location;
    private String website;
    private String birthDate;
    private String mobile;
    private String backgroundImage;
    private String bio;
    private boolean req_user; //Request User or the current user sending the request to server

    private boolean login_with_google;

    private List<UserDto> followers= new ArrayList<>();

    private List<UserDto> followings = new ArrayList<>();

    private boolean followed;//ReqUser is following this user or not

    private boolean isVerified;

    private RoleDto role;

    private LocalDateTime createdAt;
}
