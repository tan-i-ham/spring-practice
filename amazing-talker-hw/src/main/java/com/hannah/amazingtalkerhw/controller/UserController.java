package com.hannah.amazingtalkerhw.controller;

import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.exception.ResourceNotFoundException;
import com.hannah.amazingtalkerhw.payload.ApiResponse;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import com.hannah.amazingtalkerhw.security.CurrentUser;
import com.hannah.amazingtalkerhw.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/result")
    public ApiResponse getSuccessResult(@RequestParam("auth_token") String authToken) {
        return new ApiResponse(true, authToken);
    }
}
