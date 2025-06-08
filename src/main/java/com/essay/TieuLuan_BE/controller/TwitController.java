package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.TwitDto;
import com.essay.TieuLuan_BE.dto.mapper.TwitDtoMapper;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.exception.TwitException;
import com.essay.TieuLuan_BE.exception.UserException;
import com.essay.TieuLuan_BE.request.TwitReplyRequest;
import com.essay.TieuLuan_BE.response.ApiResponse;
import com.essay.TieuLuan_BE.service.TwitService;
import com.essay.TieuLuan_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twits")
public class TwitController {
    @Autowired
    private TwitService twitService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit= twitService.createTwit(req,user);

        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }
    @PostMapping("/reply")
    public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest req, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit= twitService.createReply(req,user);

        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PutMapping("/{twitId}/retwit")
    public ResponseEntity<TwitDto> reTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit= twitService.reTwit(twitId,user);

        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @GetMapping("/{twitId}")
    public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        Twit twit= twitService.findById(twitId);

        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }
    @DeleteMapping("/{twitId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        twitService.deleteTwitById(twitId,user.getId());

        ApiResponse res= new ApiResponse();
        res.setMessage("Twit deleted Successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twits= twitService.findAllTwit();

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TwitDto>> getUsersAllTwits(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserById(userId);

        List<Twit> twits= twitService.getUserTwit(user);

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserById(userId);

        List<Twit> twits= twitService.findByLikesContains(user);

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/replied")
    public ResponseEntity<List<TwitDto>> findTwitRepliedByUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Twit> twits= twitService.findRepliedByUser(user);
        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);
        return new ResponseEntity<>(twitDtos,HttpStatus.OK);
    }


}
