package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.ChangePasswordRequest;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
    //Update userInfo, get user by userId then replace old info by user's information
    public User updateUser(Long userId, User user) throws UserException;

    public User followUser(Long userId, User user) throws UserException;

    public List<User> searchUser(String query) throws UserException;

    public User changePassword(Long userId, ChangePasswordRequest request) throws UserException, BadRequestException;
}
