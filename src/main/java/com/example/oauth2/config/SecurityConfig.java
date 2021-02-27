package com.example.oauth2.config;

import com.example.oauth2.account.AccountService;
import com.example.oauth2.account.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**",
                        "/js/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.
                USER.name())
                .anyRequest().authenticated();
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(accountService);
        http
                .logout()
                .logoutSuccessUrl("/");
    }

}
