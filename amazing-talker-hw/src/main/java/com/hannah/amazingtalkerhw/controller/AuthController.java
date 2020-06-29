package com.hannah.amazingtalkerhw.controller;

import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.exception.BadRequestException;
import com.hannah.amazingtalkerhw.payload.ApiResponse;
import com.hannah.amazingtalkerhw.payload.LoginRequest;
import com.hannah.amazingtalkerhw.payload.SignUpRequest;
import com.hannah.amazingtalkerhw.service.SendMailService;
import com.hannah.amazingtalkerhw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.isDuplicateEmailAccount(signUpRequest)) {
            throw new BadRequestException("Email address already in use.");
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmedPassword())) {
            throw new BadRequestException("Password not match");
        }

        User user = userService.createUser(signUpRequest);
        sendMailService.sendRegisterSuccessMail(signUpRequest.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("result"));
        return new ResponseEntity<>(
                new ApiResponse(true, String.valueOf(user.getId())),
                headers,
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findUserByEmail(loginRequest.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("result"));
        return new ResponseEntity<>(
                new ApiResponse(true, String.valueOf(user.getId())),
                headers,
                HttpStatus.OK);
    }
}
