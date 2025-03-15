package com.essay.TieuLuan_BE.mapper;

import com.essay.TieuLuan_BE.dto.UserInfoDto;
import com.essay.TieuLuan_BE.entity.UserInfo;

public class UserInfoMapper {
    public static UserInfoDto mapUserInfoToUserInfoDto(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getProfilePicture(),
                userInfo.getBackgroundPhoto(),
                userInfo.getOverview(),
                userInfo.getLocation(),
                userInfo.getCareer(),
                userInfo.getHome()
        );
    }
}
