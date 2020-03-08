package com.hannah.springsecurityformlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author yihan.a.chen
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            // Ignore the static resource paths
                .antMatchers("/resources/", "/webjars/", "/assets/").permitAll()
                // Allow everyone to have access to the root URL "/"
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                // only users with the ADMIN role can access to /admin
                .antMatchers("/admin/").hasRole("ADMIN")
                // All other URLs should be accessible to authenticated users only
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error").permitAll()
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll()
                .deleteCookies("my-cookie-1")
                .and()
            .rememberMe()
                .key("my-secure-key")
                .rememberMeCookieName("my-cookie-1")
                .tokenValiditySeconds(24*60*60)
                .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied");
    }


}
