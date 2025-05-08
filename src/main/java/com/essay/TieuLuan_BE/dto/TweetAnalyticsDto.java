package com.essay.TieuLuan_BE.dto;

import lombok.Data;

@Data
public class TweetAnalyticsDto {
    private long totalTweets;
    private long totalRetweets;
    private long totalReplies;
    private ChartDataDto tweetsOverTimeChart;
    private ChartDataDto topTweetersChart;
}
