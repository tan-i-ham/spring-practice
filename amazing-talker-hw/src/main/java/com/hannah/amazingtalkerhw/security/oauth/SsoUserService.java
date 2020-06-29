package com.hannah.amazingtalkerhw.security.oauth;

import com.hannah.amazingtalkerhw.entity.AuthProvider;
import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.exception.OAuth2AuthenticationProcessingException;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import com.hannah.amazingtalkerhw.security.user.OAuth2UserInfo;
import com.hannah.amazingtalkerhw.security.user.OAuth2UserInfoFactory;
import com.hannah.amazingtalkerhw.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SsoUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendMailService sendMailService;

    public void upsertUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());

        // email already been registered
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            updateExistingUser(user, oAuth2UserInfo);
        } else {
            insertNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
    }

    public void insertNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setInitialCoupon(true);

        sendMailService.sendRegisterSuccessMail(oAuth2UserInfo.getEmail());

        userRepository.save(user);
    }

    public User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
