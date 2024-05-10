package com.semiuniv.semiu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //설정 타입 인식
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean //controller service repository conponant 컨포넌트 의존성 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request)->request
                        .requestMatchers("/css/**","/js/**", "/images/**").permitAll()
                        //main:indexView에서 시작 (일단)
                       .requestMatchers("/").permitAll()
                        .requestMatchers("/**").permitAll())
//                        .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
//                        .usernameParameter("email")
                        .defaultSuccessUrl("/semi/login", true))

                .logout((out)->out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout"))
                        .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
