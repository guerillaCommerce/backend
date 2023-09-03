package com.github.commerce.repository.user;

import com.github.commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String userEmail);

    boolean existsByEmail(String email);
}
