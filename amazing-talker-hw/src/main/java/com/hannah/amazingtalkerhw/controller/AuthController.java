package com.hannah.amazingtalkerhw.controller;

import com.hannah.amazingtalkerhw.entity.AuthProvider;
import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.exception.BadRequestException;
import com.hannah.amazingtalkerhw.payload.ApiResponse;
import com.hannah.amazingtalkerhw.payload.AuthResponse;
import com.hannah.amazingtalkerhw.payload.LoginRequest;
import com.hannah.amazingtalkerhw.payload.SignUpRequest;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import com.hannah.amazingtalkerhw.security.TokenProvider;
import com.hannah.amazingtalkerhw.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmedPassword())) {
            throw new BadRequestException("Password not match");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setInitialCoupon(true);

        User result = userRepository.save(user);
        sendMailService.sendRegisterSuccessMail(signUpRequest.getEmail());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();


        return ResponseEntity.created(location)
                .body(new ApiResponse(true, String.valueOf(user.getId())));
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

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
