package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.dto.mapper.UserDtoMapper;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.ChangePasswordRequest;
import com.essay.TieuLuan_BE.service.UserService;
import com.essay.TieuLuan_BE.util.util.UserUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization")String jwt) throws UserException {
        User user= userService.findUserProfileByJwt(jwt);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);

        User user= userService.findUserById(userId);

        UserDto userDto= UserDtoMapper.toUserDto(user);

        userDto.setReq_user(UserUtil.isReqUser(reqUser,user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser,user));
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);

        List<User> users= userService.searchUser(query);

        List<UserDto> userDtos= UserDtoMapper.toUserDtos(users);

        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User req, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);

        User user= userService.updateUser(reqUser.getId(),req);

        UserDto userDto= UserDtoMapper.toUserDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> followUser(@PathVariable Long userId, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);

        User user= userService.followUser(userId,reqUser);

        UserDto userDto= UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser,user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
    @GetMapping("/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@RequestHeader("Authorization") String jwt) throws UserException {
        User me = userService.findUserProfileByJwt(jwt);
        List<UserDto> followers = UserDtoMapper.toUserDtos(me.getFollowers());
        return ResponseEntity.ok(followers);
    }
    @PutMapping("/password")
    public ResponseEntity<UserDto> changePassword(@RequestBody ChangePasswordRequest req,@RequestHeader("Authorization")String jwt) throws UserException, BadRequestException {
        User reqUser= userService.findUserProfileByJwt(jwt);
        User user= userService.changePassword(reqUser.getId(),req);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

}
