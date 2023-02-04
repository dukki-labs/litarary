package com.litarary.utils.jwt;

import com.litarary.account.service.CustomUserDetailService;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.filter.JwtErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;   // 7일

    private CustomUserDetailService userDetailService;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey,
                            CustomUserDetailService userDetailService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailService = userDetailService;
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

    public Authentication getAuthentication(String token) {
        // 토근 복호화
        Claims claims = parseClaims(token);

        UserDetails userDetails = userDetailService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtErrorException(ErrorCode.EXPIRED_TOKEN);
        }
    }

    private boolean validatedRefreshToken(String refreshToken) {
        try {
            Claims claims = parseClaims(refreshToken);
            return !claims.getExpiration().before(new Date());
        } catch (Exception ex) {
            throw new JwtErrorException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    public TokenInfo refreshAccessToken(String refreshToken) {
        if (validatedRefreshToken(refreshToken)) {
            Authentication authentication = getAuthentication(refreshToken);
            return generateToken(authentication);
        }
        return null;
    }

    public TokenInfo generateToken(Authentication authenticate) {

        long nowTime = new Date().getTime();
        //권한 가져오기
        String accessToken = createAccessToken(nowTime, authenticate, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = createRefreshToken(nowTime, authenticate, REFRESH_TOKEN_EXPIRE_TIME);

        Date accessTokenExpiredDate = new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME);
        LocalDateTime accessTokenExpiredTime = LocalDateTime.ofInstant(accessTokenExpiredDate.toInstant(), ZoneId.systemDefault());
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .expiredAccessTokenAt(accessTokenExpiredTime)
                .build();
    }

    private String createAccessToken(long nowTime, Authentication authenticate, long accessTokenExpireTime) {
        Date accessTokenExpiredDate = new Date(nowTime + accessTokenExpireTime);
        String authorities = authenticate.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authenticate.getName())
                .claim("auth", authorities)
                .setIssuedAt(new Date(nowTime)) // 토근 발행 일자
                .setExpiration(accessTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshToken(long nowTime, Authentication authenticate, long refreshTokenExpireTime) {
        Date refreshTokenExpiredDate = new Date(nowTime + refreshTokenExpireTime);
        return Jwts.builder()
                .setSubject(authenticate.getName())
                .setIssuedAt(new Date(nowTime))
                .setExpiration(refreshTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
