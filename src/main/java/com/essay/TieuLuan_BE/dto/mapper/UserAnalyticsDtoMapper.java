package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.ChartDataDto;
import com.essay.TieuLuan_BE.dto.UserAnalyticsDto;
import com.essay.TieuLuan_BE.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserAnalyticsDtoMapper {
    public static UserAnalyticsDto toUserAnalyticsDto(
            long totalUsers,
            long newUsers,
            long activeUsers,
            ChartDataDto userGrowthChart,
            ChartDataDto userActivityChart,
            ChartDataDto mostFollowedUsersChart) {

        UserAnalyticsDto dto = new UserAnalyticsDto();
        dto.setTotalUsers(totalUsers);
        dto.setNewUsers(newUsers);
        dto.setActiveUsers(activeUsers);
        dto.setUserGrowthChart(userGrowthChart);
        dto.setUserActivityChart(userActivityChart);
        dto.setMostFollowedUsersChart(mostFollowedUsersChart);

        return dto;
    }
    public static ChartDataDto createMostFollowedUsersChart(Map<User, Integer> mostFollowedUsers) {
        ChartDataDto chart = new ChartDataDto();

        List<String> labels = mostFollowedUsers.keySet().stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
        series.setName("Followers");
        series.setData(mostFollowedUsers.values().stream()
                .map(Number.class::cast)
                .collect(Collectors.toList()));

        chart.setLabels(labels);
        chart.setSeries(Collections.singletonList(series));

        return chart;
    }
}
