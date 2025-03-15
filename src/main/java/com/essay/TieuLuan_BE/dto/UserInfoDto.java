package com.essay.TieuLuan_BE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String profilePicture;
    private String backgroundPhoto;
    private String overview;
    private String location;
    private String career;
    private String home;
}
