package com.example.springsecurity.filter;

import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info(" This path should not be filtered : {}",request.getServletPath());
        return new AntPathMatcher().match("/api/v1/auth/login",request.getServletPath());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("Jwt Filtering...");

        final String authorizationHeader = request.getHeader("Authorization");

        if(!(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String [] jwtToken = authorizationHeader.split(" ");

        if(jwtToken.length <= 1){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        System.out.println(JwtUtil.validateToken(jwtToken[1],"cheong"));

        System.out.println(jwtToken[1]);

        filterChain.doFilter(request,response);
    }
}
