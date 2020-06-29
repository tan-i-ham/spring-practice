package com.hannah.amazingtalkerhw.service;

import com.hannah.amazingtalkerhw.entity.AuthProvider;
import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.exception.BadRequestException;
import com.hannah.amazingtalkerhw.payload.SignUpRequest;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creating user's account
     *
     * @param signUpRequest
     * @return
     */
    public User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setInitialCoupon(true);
        userRepository.save(user);
        return user;
    }

    public boolean isDuplicateEmailAccount(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return true;
        }
        return false;
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new BadRequestException("Wrong mail");
        }
        return userOptional.get();
    }
}
