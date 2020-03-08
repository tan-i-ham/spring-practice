package com.hannah.springsecurityformlogin.repository;

import com.hannah.springsecurityformlogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author yihan.a.chen
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
