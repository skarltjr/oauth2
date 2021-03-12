package com.example.oauth2.config;

import com.example.oauth2.account.AccountService;
import com.example.oauth2.account.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf().disable() // post요청 시 csrf를 프론트단에서 넣어주기
                .authorizeRequests()
                .mvcMatchers("/", "/css/**", "/images/**","/index",
                        "/js/**","/oauth2/authorization/google","/api/events/**").permitAll()
                .anyRequest().authenticated();
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(accountService);
        /**
         * http.formLogin()
         *         .loginPage("/login").permitAll()
         *         .and()
         *     .oauth2Login();
         *     폼로그인 + 구글로그인
         * */
        http
                .logout()
                .logoutSuccessUrl("/");
    }

}
