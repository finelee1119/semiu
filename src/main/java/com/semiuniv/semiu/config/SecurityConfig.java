package com.semiuniv.semiu.config;

import com.semiuniv.semiu.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //설정 타입 인식
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean //controller service repository conponant 컨포넌트 의존성 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request)->request
                        .requestMatchers("/css/**","/js/**", "/images/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/semi/admin").hasRole("ADMIN")
                        .requestMatchers("/semi/student").hasRole("STUDENT")
                        .requestMatchers("/semi/professor").hasRole("PROFESSOR")
//                        .anyRequest().authenticated()) // 나머지 모든 요청은 인증 필요
                )

                .formLogin((form)->form
                        .loginPage("/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/login") // 로그인 처리 URL
                        .successHandler(customAuthenticationSuccessHandler)
//                        .defaultSuccessUrl("/semi/login", true) // 로그인 성공 후 이동할 URL
                        .permitAll() // 로그인 페이지는 모든 사용자 접근 허용
                )

                .logout((out)->out
                        .logoutSuccessUrl("/semi") // 로그아웃 성공 후 이동할 URL
                        .logoutUrl("/logout")
                        .permitAll() // 로그아웃 페이지는 모든 사용자 접근 허용
                )

                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        return http.build();
    }
}
