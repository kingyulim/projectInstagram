package com.projectinstagram.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userId = null;

        // 1. Authorization 헤더에서 JWT 추출
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            // JWT가 유효한지 먼저 체크
            if (jwtUtil.validateToken(token)) {

                // JWT가 존재하고 SecurityContext에 인증 정보가 이미 있으면 → 이미 로그인 상태
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    // 예외 처리: 이미 로그인된 상태
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\":\"이미 로그인된 상태입니다.\"}");
                    return; // 필터 종료
                }

                // 토큰에서 userId 추출
                userId = jwtUtil.getUserIdFromToken(token); // token은 String!

                // SecurityContext가 비어있으면 기존 인증 처리
                if (userId != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(userId));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}