package com.essay.TieuLuan_BE.dto;

import lombok.Data;

@Data
public class DashboardAnalyticsDto {
    private long totalUsers;
    private long newUsers;
    private long totalTweets;
    private long totalLikes;
    private long totalRetweets;
    private long totalReplies;
    private ChartDataDto userGrowthChart;
    private ChartDataDto activityChart;
}
