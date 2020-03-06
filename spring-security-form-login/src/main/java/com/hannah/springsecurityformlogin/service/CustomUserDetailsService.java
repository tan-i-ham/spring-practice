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
package com.hannah.springsecurityformlogin.service;

import com.hannah.springsecurityformlogin.entity.User;
import com.hannah.springsecurityformlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * @author yihan.a.chen
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            getAuthorities(user)
        );
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles()
            .stream()
            .map(role -> role.getName())
            .toArray(String[]::new);

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
