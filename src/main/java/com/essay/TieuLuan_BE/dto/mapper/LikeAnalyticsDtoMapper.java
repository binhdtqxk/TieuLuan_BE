package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.ChartDataDto;
import com.essay.TieuLuan_BE.dto.LikeAnalyticsDto;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LikeAnalyticsDtoMapper {
    public static LikeAnalyticsDto toLikeAnalyticsDto(
            long totalLikes,
            ChartDataDto likesOverTimeChart,
            ChartDataDto mostLikedTweetsChart,
            ChartDataDto mostActiveLikersChart) {

        LikeAnalyticsDto dto = new LikeAnalyticsDto();
        dto.setTotalLikes(totalLikes);
        dto.setLikesOverTimeChart(likesOverTimeChart);
        dto.setMostLikedTweetsChart(mostLikedTweetsChart);
        dto.setMostActiveLikersChart(mostActiveLikersChart);

        return dto;
    }

    public static ChartDataDto createMostLikedTweetsChart(Map<Twit, Long> mostLikedTweets) {
        ChartDataDto chart = new ChartDataDto();

        List<String> labels = mostLikedTweets.keySet().stream()
                .map(t -> t.getContent().length() > 30 ?
                        t.getContent().substring(0, 27) + "..." :
                        t.getContent())
                .collect(Collectors.toList());

        ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
        series.setName("Like Count");
        series.setData(mostLikedTweets.values().stream()
                .map(Number.class::cast)
                .collect(Collectors.toList()));

        chart.setLabels(labels);
        chart.setSeries(Collections.singletonList(series));

        return chart;
    }

    /**
     * Creates a chart of most active likers
     */
    public static ChartDataDto createMostActiveLikersChart(Map<User, Long> mostActiveLikers) {
        ChartDataDto chart = new ChartDataDto();

        List<String> labels = mostActiveLikers.keySet().stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
        series.setName("Likes Given");
        series.setData(mostActiveLikers.values().stream()
                .map(Number.class::cast)
                .collect(Collectors.toList()));

        chart.setLabels(labels);
        chart.setSeries(Collections.singletonList(series));

        return chart;
    }
}

