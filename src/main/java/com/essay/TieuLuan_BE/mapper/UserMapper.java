package com.essay.TieuLuan_BE.mapper;

import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;

public class UserMapper {
    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getGender(),
                user.getBirthDay(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    public static User mapUserDtoToUser(UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFullName(),
                userDto.getBirthday(),
                userDto.getGender(),
                userDto.getCreatedAt(),
                userDto.getUpdatedAt()
        );
    }
}
