package com.essay.TieuLuan_BE.repository;

import com.essay.TieuLuan_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
