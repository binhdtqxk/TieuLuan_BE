package com.essay.TieuLuan_BE.service.impl;

import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.mapper.UserMapper;
import com.essay.TieuLuan_BE.repository.UserRepository;
import com.essay.TieuLuan_BE.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = UserMapper.mapUserDtoToUser(userDto);
        User savedUser= userRepository.save(user);
        return UserMapper.mapUserToUserDto(savedUser);
    }
}
