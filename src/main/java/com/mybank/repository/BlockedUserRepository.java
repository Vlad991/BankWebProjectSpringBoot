package com.mybank.repository;

import com.mybank.entity.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
}
