/*
 * Copyright (c) Rakuten, Inc. All Rights Reserved.
 *
 * This program is the information assets which are handled as "Strictly Confidential".
 * Permission of Use is only admitted in Rakuten Inc Development Department.
 * If you don't have permission,
 * MUST not be published, broadcast, rewritten for broadcast or publication or redistributed
 * directly or indirectly in any medium.
 *
 */
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
