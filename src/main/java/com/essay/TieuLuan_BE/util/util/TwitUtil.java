package com.essay.TieuLuan_BE.util.util;

import com.essay.TieuLuan_BE.entity.Like;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;

public class TwitUtil {
    //Check if user liked the tweet or not
    public final static boolean isLikedByReqUser(User reqUser, Twit twit){
        for(Like like: twit.getLikes()){
            if(like.getUser().getId() == reqUser.getId()){
                return true;
            }
        }
        return false;
    }
    //Check if user retweeted the tweet or not
    public final static boolean isRetweetedByReqUser(User reqUser, Twit twit){
        for(User user: twit.getRetwitUser()){
            if(user.getId() == reqUser.getId()){
                return true;
            }
        }
        return false;
    }
}
