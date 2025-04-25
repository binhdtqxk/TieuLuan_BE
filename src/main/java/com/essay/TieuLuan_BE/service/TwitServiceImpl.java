package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.repository.TwitRepository;
import com.essay.TieuLuan_BE.request.TwitReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TwitServiceImpl implements TwitService {

    @Autowired
    private TwitRepository twitRepository;

    //Post twit
    @Override
    public Twit createTwit(Twit req, User user) throws UserException {
        Twit twit = new Twit();
        twit.setContent(req.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(req.getImage());
        twit.setUser(user);
        twit.setReply(false);
        twit.setTwit(true);
        twit.setVideo(req.getVideo());
        return twitRepository.save(twit);
    }

    //Find all twits by the day created
    @Override
    public List<Twit> findAllTwit() {
        return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
    }

    //Retwit
    @Override
    public Twit reTwit(Long twitId, User user) throws UserException, TwitException {
        Twit twit=findById(twitId);
        if(twit.getRetwitUser().contains(user)){
            twit.getRetwitUser().remove(user);
        }else{
            twit.getRetwitUser().add(user);
        }
        return twitRepository.save(twit);
    }

    //Find twit by id
    @Override
    public Twit findById(Long twitId) throws TwitException {
        Twit twit=twitRepository.findById(twitId)
                .orElseThrow(()->new TwitException("twit not found with id: "+twitId));
        return twit;
    }

    //Delete twit by id
    @Override
    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
        Twit twit=findById(twitId);
        if(!userId.equals(twit.getUser().getId())){
            throw new UserException("you can't delete another user's twit");
        }
        twitRepository.deleteById(twitId);
    }

    //Unnecessary cause already handle this in retwit
    @Override
    public Twit removeFromRetwit(Long twitId, User user) throws TwitException, UserException {
        return null;
    }

    //Reply twit
    @Override
    public Twit createReply(TwitReplyRequest req, User user) throws TwitException, UserException {
        Twit OGTwit=findById(req.getTwitId());

        Twit twit = new Twit();
        twit.setContent(req.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(req.getImage());
        twit.setUser(user);
        twit.setReply(true);
        twit.setTwit(false);
        twit.setReplyFor(OGTwit);

        Twit saveReply = twitRepository.save(twit);
        OGTwit.getReplyTwits().add(saveReply);
        twitRepository.save(OGTwit);
        return OGTwit;
    }
    //find all twit posted by specific user
    @Override
    public List<Twit> getUserTwit(User user) throws UserException {
        return twitRepository.findAllByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user,user.getId());
    }
    //Get all twit liked by specific user
    @Override
    public List<Twit> findByLikesContains(User user) throws UserException {
        return twitRepository.findByLikesUser_id(user.getId());
    }
}
