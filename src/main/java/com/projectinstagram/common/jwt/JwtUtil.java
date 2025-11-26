package com.projectinstagram.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // JWT 토큰의 접두사
    public static final String BEARER_PREFIX = "Bearer ";
    // JWT 토큰의 만료 시간 (밀리초 단위, 여기서는 60분)
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    // JWT 서명 알고리즘
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 애플리케이션 설정 파일에서 주입받은 비밀 키
    @Value("${jwt.secret}")
    private String secretKey;
    // 실제 서명에 사용되는 키 객체
    private Key key;

    /**
     * 빈 초기화 메서드
     * - 애플리케이션 시작 시 비밀 키를 Base64로 디코딩하여 Key 객체를 초기화합니다.
     */
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * JWT 토큰을 생성합니다.
     * @param userid 사용자 이름
     * @return 생성된 JWT 토큰
     */
    public String generateToken(Long userid) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .claim("userId", userid) // 사용자 고유 번호
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간 설정
                        .setIssuedAt(date) // 발급 시간 설정
                        .signWith(key, signatureAlgorithm) // 비밀 키와 알고리즘으로 서명
                        .compact(); // JWT 토큰 생성
    }

    // 회원 고유 번호 추출
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    // 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
