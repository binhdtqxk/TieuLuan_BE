package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.ChartDataDto;
import com.essay.TieuLuan_BE.dto.TweetAnalyticsDto;
import com.essay.TieuLuan_BE.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TweetAnalyticsDtoMapper {
    public static TweetAnalyticsDto toTweetAnalyticsDto(
            long totalTweets,
            long totalRetweets,
            long totalReplies,
            ChartDataDto tweetsOverTimeChart,
            ChartDataDto topTweetersChart) {

        TweetAnalyticsDto dto = new TweetAnalyticsDto();
        dto.setTotalTweets(totalTweets);
        dto.setTotalRetweets(totalRetweets);
        dto.setTotalReplies(totalReplies);
        dto.setTweetsOverTimeChart(tweetsOverTimeChart);
        dto.setTopTweetersChart(topTweetersChart);

        return dto;
    }
    public static ChartDataDto createTopTweetersChart(Map<User, Long> topTweeters) {
        ChartDataDto chart = new ChartDataDto();

        List<String> labels = topTweeters.keySet().stream()
                .map(User::getFullName)
                .collect(Collectors.toList());

        ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
        series.setName("Tweet Count");
        series.setData(topTweeters.values().stream()
                .map(Number.class::cast)
                .collect(Collectors.toList()));

        chart.setLabels(labels);
        chart.setSeries(Collections.singletonList(series));

        return chart;
    }
}
