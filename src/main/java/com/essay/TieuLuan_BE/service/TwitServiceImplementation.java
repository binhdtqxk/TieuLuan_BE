package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.TwitReplyRequest;

import java.util.List;

public class TwitServiceImplementation implements TwitService {

    @Override
    public Twit createTwit(Twit req, User user) throws UserException {
        return null;
    }

    @Override
    public List<Twit> findAllTwit() {
        return List.of();
    }

    @Override
    public Twit reTwit(Long twitId, User user) throws UserException, TwitException {
        return null;
    }

    @Override
    public Twit findById(Long twitId) throws TwitException {
        return null;
    }

    @Override
    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {

    }

    @Override
    public Twit removeFromRetwit(Long twitId, User user) throws TwitException, UserException {
        return null;
    }

    @Override
    public Twit createReply(TwitReplyRequest req, User user) throws TwitException, UserException {
        return null;
    }

    @Override
    public List<Twit> getUserTwit(User user) throws UserException {
        return List.of();
    }

    @Override
    public List<Twit> findByLikesContains(User user) throws UserException {
        return List.of();
    }
}
