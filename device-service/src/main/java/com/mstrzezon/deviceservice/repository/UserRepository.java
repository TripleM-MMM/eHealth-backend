package com.mstrzezon.deviceservice.repository;

import com.mstrzezon.deviceservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
