package com.essay.TieuLuan_BE.dto;

import lombok.Data;

@Data
public class LikeAnalyticsDto {
    private long totalLikes;
    private ChartDataDto likesOverTimeChart;
    private ChartDataDto mostLikedTweetsChart;
    private ChartDataDto mostActiveLikersChart;
}
