package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.Twit;
import com.essay.TieuLuan_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface TwitRepository extends JpaRepository<Twit, Long> {
    List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();

    List<Twit> findAllByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(User user, Long id);

    List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("select t from Twit t join t.likes l where l.user.id=:userId")
    List<Twit> findByLikesUser_id(Long userId);

    @Query(value = """
    SELECT DISTINCT tw.*
    FROM twit tw
    WHERE tw.id IN (
        SELECT b.twit_id
        FROM twit_reply_twits b
        JOIN twit t ON b.reply_twits_id = t.id
        WHERE t.is_reply = true AND t.user_id = :userId
    )
    """, nativeQuery = true)
    List<Twit> findByIs_replyUser_id(Long userId);

    Long countByIsTwitFalseAndIsReplyFalse();

    Long countByIsReplyTrue();

    Long countByCreatedAtAfter(LocalDateTime startDate);

    Long countByIsTwitFalseAndIsReplyFalseAndCreatedAtAfter(LocalDateTime startDate);

    Long countByIsReplyTrueAndCreatedAtAfter(LocalDateTime startDate);

    Long countByIsTwitTrueAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Twit> findByCreatedAtAfter(LocalDateTime startDate);

    Long countByIsTwitFalseAndIsReplyFalseAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Long countByIsReplyTrueAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
