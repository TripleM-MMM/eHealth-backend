package com.mstrzezon.userservice.repository;

import com.mstrzezon.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
