package com.projectinstagram.common.jwt;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.user.dto.response.TokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUrl = request.getRequestURI();

        /**
         * 로그인/회원가입은 토큰이 없는 경우만 허용
         *
         */
        if (requestUrl.equals("/login") || requestUrl.equals("/join")) {
            filterChain.doFilter(request, response);

            return;
        }

        /**
         * Authorization 가져오기
         */
        String authorizationHeader = request.getHeader("Authorization");

        /**
         * 토큰 확인 조건
         */
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(ExceptionMessageEnum.TOKEN_CHECK);
        }

        /**
         * Bearer 제거
         */
        String tokenSubstring = authorizationHeader.substring(7);

        /**
         * 토큰 검증 만료시간 조건 처리
         */
        if (!jwtUtil.validateToken(tokenSubstring)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        }

        /**
         * 토큰 유효성 검사
         */
        if (!jwtUtil.validateToken(tokenSubstring)) {
            throw new IllegalArgumentException("유효하지 않는 토큰 입니다.");
        }

        Long userId = Long.valueOf(jwtUtil.getUserId(tokenSubstring));
        String name = jwtUtil.getName(tokenSubstring);
        String nickname = jwtUtil.getNickName(tokenSubstring);

        TokenResponse tokenResponse = new TokenResponse(userId, name, nickname);

        request.setAttribute("thisToken", tokenResponse);

        filterChain.doFilter(request, response);
    }

}
