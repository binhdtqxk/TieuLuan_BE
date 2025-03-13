package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/api/SignUp")
public class UserController {

    private UserService userService;

    //build Sign up REST API
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        UserDto savedUser= userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
