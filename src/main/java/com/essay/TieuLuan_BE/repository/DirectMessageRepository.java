package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.DirectMessage;
import com.essay.TieuLuan_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {


    @Query("""
    SELECT m FROM DirectMessage m 
    WHERE (m.sender.id = :me AND m.recipient.id = :other)
       OR (m.sender.id = :other AND m.recipient.id = :me)
    ORDER BY m.createdAt
  """)
    List<DirectMessage> findConversation(
            @Param("me") Long me,
            @Param("other") Long other
    );

    List<DirectMessage> findBySenderOrRecipientOrderByCreatedAtDesc(User sender, User recipient);
}
