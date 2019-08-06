package com.mybank.repository;

import com.mybank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Modifying
    @Query("DELETE FROM blocked_users b WHERE b.user = :user")
    void removeBlocked(@Param("user") User user);
}
