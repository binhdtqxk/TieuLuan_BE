package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.dto.*;
import com.essay.TieuLuan_BE.entity.TimeRange;

import java.util.List;

public interface AdminService {

    DashboardAnalyticsDto getDashboardAnalytics(TimeRange timeRange);

    TweetAnalyticsDto getTweetAnalytics(TimeRange timeRange);

    LikeAnalyticsDto getLikeAnalytics(TimeRange timeRange);

    UserAnalyticsDto getUserAnalytics(TimeRange timeRange);

    List<UserDto> getAllUsers(String query, int page, int size);

    UserDto banUser(Long userId, String reason);

    UserDto unbanUser(Long userId);
}
