package com.essay.TieuLuan_BE.entity.analytics;

import lombok.Data;

@Data
public class DashboardAnalytics {
    private long totalUsers;
    private long newUsers;
    private long totalTweets;
    private long totalLikes;
    private long totalRetweets;
    private long totalReplies;
    private ChartData userGrowthChart;
    private ChartData activityChart;
}
