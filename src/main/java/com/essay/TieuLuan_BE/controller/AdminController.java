package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.dto.*;
import com.essay.TieuLuan_BE.request.BanUserRequestDto;
import com.essay.TieuLuan_BE.service.AdminService;
import com.essay.TieuLuan_BE.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/analytics/dashboard")
    public ResponseEntity<DashboardAnalyticsDto> getDashboardAnalytics() {
        DashboardAnalyticsDto analytics = adminService.getDashboardAnalytics();
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }

    @GetMapping("/analytics/tweets")
    public ResponseEntity<TweetAnalyticsDto> getTweetAnalytics() {
        TweetAnalyticsDto analytics = adminService.getTweetAnalytics();
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }

    @GetMapping("/analytics/likes")
    public ResponseEntity<LikeAnalyticsDto> getLikeAnalytics() {
        LikeAnalyticsDto analytics = adminService.getLikeAnalytics();
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }

    @GetMapping("/analytics/users")
    public ResponseEntity<UserAnalyticsDto> getUserAnalytics() {
        UserAnalyticsDto analytics = adminService.getUserAnalytics();
        return new ResponseEntity<>(analytics, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        List<UserDto> users = adminService.getAllUsers(query, page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/ban")
    public ResponseEntity<UserDto> banUser(
            @PathVariable Long userId,
            @RequestBody BanUserRequestDto request) {

        UserDto bannedUser = adminService.banUser(userId, request.getReason());
        return new ResponseEntity<>(bannedUser, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unban")
    public ResponseEntity<UserDto> unbanUser(@PathVariable Long userId) {
        UserDto unbannedUser = adminService.unbanUser(userId);
        return new ResponseEntity<>(unbannedUser, HttpStatus.OK);
    }
}
