package com.hannah.amazingtalkerhw.security.oauth;

import com.hannah.amazingtalkerhw.config.AppProperties;
import com.hannah.amazingtalkerhw.entity.User;
import com.hannah.amazingtalkerhw.repository.UserRepository;
import com.hannah.amazingtalkerhw.security.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private String resultUrl = "http://localhost:8080/result";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        User user = userRepository.findByEmail(email).get();

        String token = Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();

        String redirectionUrl = UriComponentsBuilder.fromUriString(resultUrl)
                .queryParam("auth_token", user.getId())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }
}
