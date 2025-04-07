package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.LikeDto;
import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.Like;
import com.essay.TieuLuan_BE.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {
    public static LikeDto toLikeDto(Like like, User reqUser){
        UserDto user= UserDtoMapper.toUserDto(like.getUser());
        UserDto reqUserDto= UserDtoMapper.toUserDto(reqUser);
        TwitDto twit= TwitDtoMapper.toTwitDto(like.getTwit(),reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTwit(twit);
        likeDto.setUser(user);


        return likeDto;
    }
    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser){
        List<LikeDto> likeDtos= new ArrayList<LikeDto>();
        for(Like like: likes){
            UserDto user= UserDtoMapper.toUserDto(like.getUser());
            TwitDto twit= TwitDtoMapper.toTwitDto(like.getTwit(),reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setTwit(twit);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
