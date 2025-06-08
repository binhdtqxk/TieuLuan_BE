package com.essay.TieuLuan_BE.service;

import com.essay.TieuLuan_BE.dto.*;
import com.essay.TieuLuan_BE.dto.mapper.*;
import com.essay.TieuLuan_BE.entity.Like;
import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.entity.Verification;
import com.essay.TieuLuan_BE.repository.LikeRepository;
import com.essay.TieuLuan_BE.repository.TwitRepository;
import com.essay.TieuLuan_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwitRepository twitRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public DashboardAnalyticsDto getDashboardAnalytics() {
        LocalDateTime startDate = getMonthStartDate();

        long totalUsers = userRepository.count();
        long newUsers = userRepository.countByCreatedAtAfter(startDate);
        long totalTweets = twitRepository.count();
        long totalLikes = likeRepository.count();
        long totalRetweets = twitRepository.countByIsTwitFalseAndIsReplyFalse();
        long totalReplies = twitRepository.countByIsReplyTrue();

        List<String> weekLabels = ChartDataDtoMapper.generateWeeklyLabels();
        List<Number> userCounts = getUserCountsForMonth();
        ChartDataDto userGrowthChart = ChartDataDtoMapper.createTimeSeriesChart(
                weekLabels, "New Users", userCounts);

        List<String> activityLabels = weekLabels;
        List<String> seriesNames = Arrays.asList("Tweets", "Likes");
        List<List<Number>> seriesData = Arrays.asList(
                getTweetCountsForMonth(),
                getLikeCountsForMonth()
        );
        ChartDataDto activityChart = ChartDataDtoMapper.createMultiSeriesChart(
                activityLabels, seriesNames, seriesData);

        return DashboardAnalyticsDtoMapper.toDashboardAnalyticsDto(
                totalUsers, newUsers, totalTweets, totalLikes, totalRetweets,
                totalReplies, userGrowthChart, activityChart);
    }

    @Override
    public TweetAnalyticsDto getTweetAnalytics() {
        LocalDateTime startDate = getMonthStartDate();

        long totalTweets = twitRepository.countByCreatedAtAfter(startDate);
        long totalRetweets = twitRepository.countByIsTwitFalseAndIsReplyFalseAndCreatedAtAfter(startDate);
        long totalReplies = twitRepository.countByIsReplyTrueAndCreatedAtAfter(startDate);

        List<String> weekLabels = ChartDataDtoMapper.generateWeeklyLabels();
        List<String> seriesNames = Arrays.asList("Original Tweets", "Replies");
        List<List<Number>> seriesData = Arrays.asList(
                getTweetCountsForMonth(),
                getReplyCountsForMonth()
        );
        ChartDataDto tweetsOverTimeChart = ChartDataDtoMapper.createMultiSeriesChart(
                weekLabels, seriesNames, seriesData);

        Map<User, Long> topTweeters = getTopTweeters(startDate);
        ChartDataDto topTweetersChart = TweetAnalyticsDtoMapper.createTopTweetersChart(topTweeters);

        return TweetAnalyticsDtoMapper.toTweetAnalyticsDto(
                totalTweets, totalRetweets, totalReplies, tweetsOverTimeChart, topTweetersChart);
    }


    public LikeAnalyticsDto getLikeAnalytics() {
        LocalDateTime startDate = getMonthStartDate();

        long totalLikes = likeRepository.countByCreatedAtAfter(startDate);

        List<String> weekLabels = ChartDataDtoMapper.generateWeeklyLabels();
        List<Number> likeCounts = getLikeCountsForMonth();
        ChartDataDto likesOverTimeChart = ChartDataDtoMapper.createTimeSeriesChart(
                weekLabels, "Likes", likeCounts);

        Map<Twit, Long> mostLikedTweets = getMostLikedTweets(startDate);
        ChartDataDto mostLikedTweetsChart = LikeAnalyticsDtoMapper.createMostLikedTweetsChart(mostLikedTweets);

        Map<User, Long> mostActiveLikers = getMostActiveLikers(startDate);
        ChartDataDto mostActiveLikersChart = LikeAnalyticsDtoMapper.createMostActiveLikersChart(mostActiveLikers);

        return LikeAnalyticsDtoMapper.toLikeAnalyticsDto(
                totalLikes, likesOverTimeChart, mostLikedTweetsChart, mostActiveLikersChart);
    }


    public UserAnalyticsDto getUserAnalytics() {
        LocalDateTime startDate = getMonthStartDate();

        long totalUsers = userRepository.count();
        long newUsers = userRepository.countByCreatedAtAfter(startDate);
        long activeUsers = getActiveUserCount(startDate);

        List<String> weekLabels = ChartDataDtoMapper.generateWeeklyLabels();
        List<Number> userCounts = getUserCountsForMonth();
        ChartDataDto userGrowthChart = ChartDataDtoMapper.createTimeSeriesChart(
                weekLabels, "New Users", userCounts);

        List<Number> activeUserCounts = getActiveUserCountsForMonth();
        ChartDataDto userActivityChart = ChartDataDtoMapper.createTimeSeriesChart(
                weekLabels, "Active Users", activeUserCounts);

        Map<User, Integer> mostFollowedUsers = getMostFollowedUsers();
        ChartDataDto mostFollowedUsersChart = UserAnalyticsDtoMapper.createMostFollowedUsersChart(mostFollowedUsers);

        return UserAnalyticsDtoMapper.toUserAnalyticsDto(
                totalUsers, newUsers, activeUsers, userGrowthChart, userActivityChart, mostFollowedUsersChart);
    }


    public List<UserDto> getAllUsers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users;

        if (query != null && !query.isEmpty()) {
            users = userRepository.findByFullNameContainingOrEmailContaining(query, query, pageable);
        } else {
            users = userRepository.findAll(pageable).getContent();
        }

        return users.stream()
                .map(UserDtoMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto banUser(Long userId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getVerification() == null) {
            user.setVerification(new Verification());
        }

        user.getVerification().setStatus(false);
        user.getVerification().setPlanType("BANNED: " + reason);
        user.getVerification().setStartedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return UserDtoMapper.toUserDto(savedUser);
    }

    @Override
    public UserDto unbanUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getVerification() != null) {
            user.getVerification().setStatus(true);
            user.getVerification().setPlanType(null);
            user.getVerification().setEndsAt(LocalDateTime.now());
        }

        User savedUser = userRepository.save(user);
        return UserDtoMapper.toUserDto(savedUser);
    }

    private LocalDateTime getMonthStartDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }

    private List<Number> getUserCountsForMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = getMonthStartDate();
        List<Number> counts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDateTime weekStart = monthStart.plusWeeks(i);
            LocalDateTime weekEnd = weekStart.plusWeeks(1);

            if (weekEnd.isAfter(now)) {
                weekEnd = now;
            }

            long count = userRepository.countByCreatedAtBetween(weekStart, weekEnd);
            counts.add(count);
        }

        return counts;
    }

    private List<Number> getTweetCountsForMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = getMonthStartDate();
        List<Number> counts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDateTime weekStart = monthStart.plusWeeks(i);
            LocalDateTime weekEnd = weekStart.plusWeeks(1);

            if (weekEnd.isAfter(now)) {
                weekEnd = now;
            }

            long count = twitRepository.countByIsTwitTrueAndCreatedAtBetween(weekStart, weekEnd);
            counts.add(count);
        }

        return counts;
    }


    private List<Number> getReplyCountsForMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = getMonthStartDate();
        List<Number> counts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDateTime weekStart = monthStart.plusWeeks(i);
            LocalDateTime weekEnd = weekStart.plusWeeks(1);

            if (weekEnd.isAfter(now)) {
                weekEnd = now;
            }

            long count = twitRepository.countByIsReplyTrueAndCreatedAtBetween(weekStart, weekEnd);
            counts.add(count);
        }

        return counts;
    }

    private List<Number> getLikeCountsForMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = getMonthStartDate();
        List<Number> counts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDateTime weekStart = monthStart.plusWeeks(i);
            LocalDateTime weekEnd = weekStart.plusWeeks(1);

            if (weekEnd.isAfter(now)) {
                weekEnd = now;
            }

            long count = likeRepository.countByCreatedAtBetween(weekStart, weekEnd);
            counts.add(count);
        }

        return counts;
    }

    private List<Number> getActiveUserCountsForMonth() {
        return Arrays.asList(35, 42, 38, 45);
    }

    private Map<User, Long> getTopTweeters(LocalDateTime since) {
        List<Twit> recentTwits = twitRepository.findByCreatedAtAfter(since);

        Map<User, Long> userTweetCounts = recentTwits.stream()
                .filter(t -> t.getUser() != null)
                .collect(Collectors.groupingBy(Twit::getUser, Collectors.counting()));

        return userTweetCounts.entrySet().stream()
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<Twit, Long> getMostLikedTweets(LocalDateTime since) {
        List<Like> recentLikes = likeRepository.findByCreatedAtAfter(since);

        Map<Twit, Long> twitLikeCounts = recentLikes.stream()
                .filter(l -> l.getTwit() != null)
                .collect(Collectors.groupingBy(Like::getTwit, Collectors.counting()));

        return twitLikeCounts.entrySet().stream()
                .sorted(Map.Entry.<Twit, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Map<User, Long> getMostActiveLikers(LocalDateTime since) {
        List<Like> recentLikes = likeRepository.findByCreatedAtAfter(since);

        Map<User, Long> userLikeCounts = recentLikes.stream()
                .filter(l -> l.getUser() != null)
                .collect(Collectors.groupingBy(Like::getUser, Collectors.counting()));

        return userLikeCounts.entrySet().stream()
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private long getActiveUserCount(LocalDateTime since) {
        Set<User> activeTweeters = twitRepository.findByCreatedAtAfter(since).stream()
                .map(Twit::getUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<User> activeLikers = likeRepository.findByCreatedAtAfter(since).stream()
                .map(Like::getUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<User> allActiveUsers = new HashSet<>();
        allActiveUsers.addAll(activeTweeters);
        allActiveUsers.addAll(activeLikers);

        return allActiveUsers.size();
    }

    private Map<User, Integer> getMostFollowedUsers() {
        List<User> allUsers = userRepository.findAll();

        Map<User, Integer> followerCounts = new HashMap<>();
        for (User user : allUsers) {
            followerCounts.put(user, user.getFollowers().size());
        }

        return followerCounts.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
