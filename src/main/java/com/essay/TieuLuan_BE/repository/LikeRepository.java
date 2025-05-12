package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query("select l from Like l where l.user.id=:userId and l.twit.id=:twitId") //Check if user already liked the tweet or nah
     Like isLikeExist(@Param("userId")Long userId,@Param("twitId")Long twitId);
    @Query("select l from Like l where l.twit.id=:twitId")
     List<Like> findBytwitId(@Param("twitId")Long twitId);
     Long countByCreatedAtAfter(LocalDateTime startDate);
     Long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Like> findByCreatedAtAfter(LocalDateTime startDate);
}
