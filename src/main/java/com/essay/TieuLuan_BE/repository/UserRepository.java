package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail (String email);

    @Query("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    public List<User> searchUser(@Param("query")String query);

    public Long countByCreatedAtAfter(LocalDateTime startDate);
    public List<User> findByFullNameContainingOrEmailContaining(String fullNameQuery, String emailQuery, Pageable pageable);
    public Long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
