package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.config.JwtProvider;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.repository.UserRepository;
import com.essay.TieuLuan_BE.request.ChangePasswordRequest;
import com.essay.TieuLuan_BE.service.notificationService.NotificationService;
import com.essay.TieuLuan_BE.service.notificationService.NotificationType;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    NotificationService notificationService;
    @Override
    public User findUserById(Long userId) throws UserException {
        User user= userRepository.findById(userId)
                .orElseThrow(()->new UserException("User not found with id: "+userId));
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user= userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with email: "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User req) throws UserException {
        User user=findUserById(userId);
        if(req.getFullName()!=null){
            user.setFullName(req.getFullName());
        }
        if(req.getImage()!=null){
            user.setImage(req.getImage());
        }
        if(req.getBackgroundImage()!=null){
            user.setBackgroundImage(req.getBackgroundImage());
        }
        if(req.getBirthDate()!=null){
            user.setBirthDate(req.getBirthDate());
        }
        if(req.getLocation()!=null){
            user.setLocation(req.getLocation());
        }
        if(req.getBio()!=null){
            user.setBio(req.getBio());
        }
        if(req.getWebsite()!=null){
            user.setWebsite(req.getWebsite());
        }
        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {
        User followToUser=findUserById(userId);

        if(user.getFollowing().contains(followToUser)&&followToUser.getFollowers().contains(user)){
            user.getFollowing().remove(followToUser);
            followToUser.getFollowers().remove(user);
        }else{
            user.getFollowing().add(followToUser);
            followToUser.getFollowers().add(user);
        }
        if(userId==user.getId()){
//            System.out.println("bat dau tao ");
            notificationService.sendNotification(NotificationType.FOLLOW, user,followToUser,null);
//            System.out.println("gui xong");
        }
        userRepository.save(followToUser);
        userRepository.save(user);
        return followToUser;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        return userRepository.searchUser(query);
    }

    @Override
    public User changePassword(Long userId, ChangePasswordRequest request) throws UserException, BadRequestException {
        User user= findUserById(userId);
        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new BadRequestException("Current password is not correct");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        return userRepository.save(user);
    }
}
