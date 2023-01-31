package com.litarary.utils.jwt;

import com.litarary.common.ErrorCode;
import com.litarary.exception.filter.JwtErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;   // 7일

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            // 토큰 검증
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException | IllegalArgumentException e) {
            throw new JwtErrorException(ErrorCode.INVALIDATED_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new JwtErrorException(ErrorCode.EXPIRED_TOKEN, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtErrorException(ErrorCode.UNSUPPORTED_TOKEN, e.getMessage());
        }
    }

    public Authentication getAuthentication(String accessToken) {
        // 토근 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new JwtErrorException(ErrorCode.AUTH_INVALIDATED_TOKEN);
        }
        List<SimpleGrantedAuthority> authorityList = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorityList);
        return new UsernamePasswordAuthenticationToken(principal, "", authorityList);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public TokenInfo generateToken(Authentication authenticate) {

        //권한 가져오기
        String authorities = authenticate.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long nowTime = new Date().getTime();
        Date accessTokenExpiredDate = new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authenticate.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Date refreshTokenExpiredDate = new Date(nowTime + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LocalDateTime accessTokenExpiredTime = LocalDateTime.ofInstant(accessTokenExpiredDate.toInstant(), ZoneId.systemDefault());
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .expiredAccessTokenAt(accessTokenExpiredTime)
                .build();
    }
}
