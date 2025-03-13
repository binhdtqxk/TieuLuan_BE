package com.essay.TieuLuan_BE.mapper;

import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;

public class UserMapper {
    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getProfilePicture(),
                user.getBackgroundPhoto(),
                user.getOverview(),
                user.getLocation(),
                user.getCareer(),
                user.getCreatedAt(),
                user.getHome(),
                user.getUpdatedAt()
        );
    }
    public static User mapUserDtoToUser(UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFullName(),
                userDto.getProfilePicture(),
                userDto.getBackgroundPhoto(),
                userDto.getOverview(),
                userDto.getLocation(),
                userDto.getCareer(),
                userDto.getCreatedAt(),
                userDto.getHome(),
                userDto.getUpdatedAt()
        );
    }
}
