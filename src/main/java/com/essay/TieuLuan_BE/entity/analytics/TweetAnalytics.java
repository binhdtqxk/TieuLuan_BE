package com.essay.TieuLuan_BE.entity.analytics;

import lombok.Data;

@Data
public class TweetAnalytics {
    private long totalTweets;
    private long totalRetweets;
    private long totalReplies;
    private ChartData tweetsOverTimeChart;
    private ChartData topTweetersChart;
}
