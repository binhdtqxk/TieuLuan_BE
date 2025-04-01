package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.Like;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;

import java.util.List;

public interface LikeService {
    //Like twit
    public Like likeTwit(Long twitid, User user) throws UserException, TwitException;

    //Get likes of specific twit
    public List<Like> getAllLikes(Long twitId) throws TwitException;

}
