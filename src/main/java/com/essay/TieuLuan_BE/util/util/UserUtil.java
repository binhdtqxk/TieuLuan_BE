package com.essay.TieuLuan_BE.util.util;

import com.essay.TieuLuan_BE.entity.User;

public class UserUtil {
    public static final boolean isReqUser(User reqUser, User user2){
        return reqUser.getId().equals(user2.getId());
    }
    //Check if request User follow the user who are request user searching for
    public static final boolean isFollowedByReqUser(User reqUser, User user2){
        return reqUser.getFollowing().contains(user2);
    }
}
