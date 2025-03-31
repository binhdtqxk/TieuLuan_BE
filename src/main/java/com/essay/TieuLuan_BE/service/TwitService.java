package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.TwitReplyRequest;

import java.util.List;

public interface TwitService {
    public Twit createTwit(Twit req, User user) throws UserException;
    public List<Twit> findAllTwit();
    public Twit reTwit(Long twitId, User user) throws UserException, TwitException;
    public Twit findById(Long twitId) throws TwitException;

    public void deleteTwitById(Long twitId,Long userId) throws TwitException, UserException;

    public Twit removeFromRetwit(Long twitId,User user) throws TwitException, UserException;

    public Twit createReply(TwitReplyRequest req, User user)throws TwitException, UserException;

    public List<Twit> getUserTwit(User user) throws UserException;

    public List<Twit> findByLikesContains(User user) throws UserException;



}
