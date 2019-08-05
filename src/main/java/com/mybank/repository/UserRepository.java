package com.mybank.repository;

import com.mybank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

//    @Modifying
//    @Query("DELETE FROM Ban b WHERE b.user = :user")
//    void removeBan(@Param("user") User user);
}
