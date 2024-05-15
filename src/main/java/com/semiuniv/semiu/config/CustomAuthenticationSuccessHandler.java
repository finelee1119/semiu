package com.semiuniv.semiu.config;

import com.semiuniv.semiu.constant.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains(UserRole.ADMIN.name())) {
            response.sendRedirect("/semi/admin/home");
        } else if (roles.contains(UserRole.STUDENT.name())) {
            response.sendRedirect("/semi/student/home");
        } else if (roles.contains(UserRole.PROFESSOR.name())) {
            response.sendRedirect("/semi/professor/home");
        } else {
            response.sendRedirect("/login?error");
        }
    }

}
