package com.essay.TieuLuan_BE.entity.analytics;

import lombok.Data;

@Data
public class LikeAnalytics {
    private long totalLikes;
    private ChartData likesOverTimeChart;
    private ChartData mostLikedTweetsChart;
    private ChartData mostActiveLikersChart;
}
