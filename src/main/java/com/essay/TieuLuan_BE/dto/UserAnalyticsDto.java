package com.essay.TieuLuan_BE.dto;

import lombok.Data;

@Data
public class UserAnalyticsDto {
    private long totalUsers;
    private long newUsers;
    private long activeUsers;
    private ChartDataDto userGrowthChart;
    private ChartDataDto userActivityChart;
    private ChartDataDto mostFollowedUsersChart;
}
