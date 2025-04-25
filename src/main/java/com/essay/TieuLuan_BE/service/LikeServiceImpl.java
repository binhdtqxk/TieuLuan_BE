package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.Like;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.service.notificationService.NotificationService;
import com.essay.TieuLuan_BE.service.notificationService.NotificationType;
import com.essay.TieuLuan_BE.repository.LikeRepository;
import com.essay.TieuLuan_BE.repository.TwitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    NotificationService notificationService;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TwitService twitService;
    @Autowired
    private TwitRepository twitRepository;
    //Like specific twit by specific user
    @Override
    public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
        System.out.println("bat dau vao liketwit");
        Like islikeExist = likeRepository.isLikeExist(user.getId(), twitId);
        if (islikeExist != null) {
            likeRepository.deleteById(islikeExist.getId()); //if user already likes it then unlike
            return islikeExist;
        }
        Twit twit=twitService.findById(twitId);
        Like like=new Like();
        like.setTwit(twit);
        like.setUser(user);
        Like savedLike = likeRepository.save(like);
        twit.getLikes().add(savedLike);
        twitRepository.save(twit);
        if(!Objects.equals(twit.getUser().getId(), user.getId())){
            System.out.println("bat dau tao ");
            User recipient=twit.getUser();
            notificationService.sendNotification(NotificationType.LIKE, user,recipient,twit);
            System.out.println("gui xong");
        }
        return savedLike;
    }
    //Get likes of a specific twit
    @Override
    public List<Like> getAllLikes(Long twitId) throws TwitException {
        List<Like> likes=likeRepository.findBytwitId(twitId);
        return likes;
    }
}
