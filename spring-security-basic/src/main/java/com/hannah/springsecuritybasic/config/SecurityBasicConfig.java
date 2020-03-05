package com.hannah.springsecuritybasic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yihan.a.chen
 */
@Configuration
@EnableWebSecurity // enable Spring Security’s web security support and provide the Spring MVC integration
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {

    /**
     * defines which URL paths should be secured and which should not
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic() // enable http basic authentication
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated(); // every request need to be authenticated then can access to endpoint

    }
}