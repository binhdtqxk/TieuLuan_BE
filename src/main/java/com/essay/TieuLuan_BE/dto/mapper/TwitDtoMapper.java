package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.util.util.TwitUtil;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;

import java.util.ArrayList;
import java.util.List;

public class TwitDtoMapper {
    public static TwitDto toTwitDto(Twit twit, User reqUser) {
        UserDto user= UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked= TwitUtil.isLikedByReqUser(reqUser, twit);
        boolean isRetweeted=TwitUtil.isRetweetedByReqUser(reqUser, twit);

        List<Long> retwitUserId=new ArrayList<Long>();
        for(User user1: twit.getRetwitUser()){
            retwitUserId.add(user1.getId());
        }

        TwitDto twitDto=new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLike(twit.getLikes().size());
        twitDto.setTotalReply(twit.getReplyTwits().size());
        twitDto.setTotalRetweets(twit.getRetwitUser().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setRetwit(isRetweeted);
        twitDto.setRetwitUsersId(retwitUserId);
        twitDto.setReplyTwits(toTwitDtos(twit.getReplyTwits(),reqUser));
        twitDto.setVideo(twit.getVideo());
        twitDto.setLocation(twit.getLocation());

        return twitDto;
    }
    public static List<TwitDto> toTwitDtos(List<Twit> twits, User reqUser){
        List<TwitDto> twitDtos=new ArrayList<>();
        for(Twit twit: twits){
            TwitDto twitDto=toReplyTwitDto(twit,reqUser);
            twitDtos.add(twitDto);
        }
        return twitDtos;
    }

    private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {
        UserDto user= UserDtoMapper.toUserDto(twit.getUser());
        boolean isLiked= TwitUtil.isLikedByReqUser(reqUser, twit);
        boolean isRetweeted=TwitUtil.isRetweetedByReqUser(reqUser, twit);

        List<Long> retwitUserId=new ArrayList<Long>();
        for(User user1: twit.getRetwitUser()){
            retwitUserId.add(user1.getId());
        }

        TwitDto twitDto=new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLike(twit.getLikes().size());
        twitDto.setTotalReply(twit.getReplyTwits().size());
        twitDto.setTotalRetweets(twit.getRetwitUser().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setRetwit(isRetweeted);
        twitDto.setRetwitUsersId(retwitUserId);
        twitDto.setVideo(twit.getVideo());
        twitDto.setLocation(twit.getLocation());

        return twitDto;
    }
}
