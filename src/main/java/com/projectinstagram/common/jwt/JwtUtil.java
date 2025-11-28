package com.projectinstagram.common.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {
    @Value("${jwt.secret.key}")
    private String secretKeyValue;

    /**
     * 토큰 만료 시간
     */
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    private SecretKey secretKey;
    private JwtParser jwtParser;

    /**
     * 내가 지정한 문자열을 jwt에서 사용하는 객체로 변환하는 메서드
     */
    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(secretKeyValue);

        secretKey =  Keys.hmacShaKeyFor(bytes);
        jwtParser = Jwts
                .parser()
                .verifyWith(this.secretKey)
                .build();
    }

    /**
     * 토큰 생성 메서드
     * @param userId 회원 고유 번호 파라미터
     * @return Jwts 토큰 반환
     */
    public String generateToken(
            Long userId,
            String name,
            String nickName
    ) {
        Date date = new Date();

        return Jwts
                .builder()
                .claim("userId", userId)
                .claim("name", name)
                .claim("nickName", nickName)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료시간
                .setIssuedAt(date) // 발급시간
                .signWith(secretKey, SIG.HS256)
                .compact();
    }

    /**
     * 토큰 안에 있는 정보 userId 뽑아오기
     * @param jwt
     * @return userId
     */
    public Long getUserId(String jwt) {
        return jwtParser
                .parseSignedClaims(jwt)
                .getPayload()
                .get("userId", Long.class);
    }

    /**
     * 토큰 안에 있는 정보 name 뽑아오기
     * @param jwt
     * @return userId
     */
    public String getName(String jwt) {
        return jwtParser
                .parseSignedClaims(jwt)
                .getPayload()
                .get("name", String.class);
    }

    /**
     * 토큰 안에 있는 정보 nickName 뽑아오기
     * @param jwt
     * @return userId
     */
    public String getNickName(String jwt) {
        return jwtParser
                .parseSignedClaims(jwt)
                .getPayload()
                .get("nickName", String.class);
    }

    /**
     * 토큰 검증 메서드
     * @param jwt
     * @return
     */
    public boolean validateToken(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            return false;
        }

        try {
            jwtParser.parseSignedClaims(jwt);
            return true;
        } catch (Exception e) {
            // 삐빗 오류 발생 이거 유효하지 않아
            return false;
        }
    }
}
