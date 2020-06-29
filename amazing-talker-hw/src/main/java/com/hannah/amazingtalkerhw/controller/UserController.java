package com.hannah.amazingtalkerhw.controller;

import com.hannah.amazingtalkerhw.payload.ApiResponse;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/result")
    public ApiResponse getSuccessResult(@RequestParam("auth_token") String authToken) {
        return new ApiResponse(true, authToken);
    }
}
