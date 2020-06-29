package com.hannah.amazingtalkerhw.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidc2UserService extends OidcUserService {

    @Autowired
    private SsoUserService ssoUserService;

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        ssoUserService.upsertUser(oidcUserRequest, oidcUser);
        return oidcUser;
    }


}
