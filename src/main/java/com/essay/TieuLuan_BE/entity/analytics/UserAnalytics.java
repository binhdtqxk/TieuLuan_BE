package com.essay.TieuLuan_BE.entity.analytics;

import lombok.Data;

@Data
public class UserAnalytics {
    private long totalUsers;
    private long newUsers;
    private long activeUsers;
    private ChartData userGrowthChart;
    private ChartData userActivityChart;
    private ChartData mostFollowedUsersChart;

}
