package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.ChartDataDto;
import com.essay.TieuLuan_BE.dto.DashboardAnalyticsDto;

public class DashboardAnalyticsDtoMapper {
    public static DashboardAnalyticsDto toDashboardAnalyticsDto(
            long totalUsers,
            long newUsers,
            long totalTweets,
            long totalLikes,
            long totalRetweets,
            long totalReplies,
            ChartDataDto userGrowthChart,
            ChartDataDto activityChart) {

        DashboardAnalyticsDto dto = new DashboardAnalyticsDto();
        dto.setTotalUsers(totalUsers);
        dto.setNewUsers(newUsers);
        dto.setTotalTweets(totalTweets);
        dto.setTotalLikes(totalLikes);
        dto.setTotalRetweets(totalRetweets);
        dto.setTotalReplies(totalReplies);
        dto.setUserGrowthChart(userGrowthChart);
        dto.setActivityChart(activityChart);

        return dto;
    }
}
