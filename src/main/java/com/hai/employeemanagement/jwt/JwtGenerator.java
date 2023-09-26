package com.hai.employeemanagement.jwt;

import com.hai.employeemanagement.converter.UserConverter;
import com.hai.employeemanagement.dto.login.AuthResponseDTO;
import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtGenerator {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final HttpServletResponse httpServletResponse;

    public AuthResponseDTO generateToken(Authentication authentication) {
        String username = authentication.getName();
        UserEntity entity = userRepository.findOneByUsername(username);
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(currentDate.getTime() + JwtConstant.JWT_EXPIRATION);
        Date expiredDate_refreshToken = new Date(currentDate.getTime() + JwtConstant.REFRESH_TOKEN_EXPIRATION);
        String accessToken = Jwts.builder()
                .setClaims(Map.of("role", entity.getRoles(), "name", username))
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(getSignInKey(JwtConstant.JWT_SECRET), SignatureAlgorithm.HS256)
                .compact();
        String refreshToken = Jwts.builder()
                .setClaims(Map.of("role", entity.getRoles(), "name", username))
                .setIssuedAt(new Date())
                .setExpiration(expiredDate_refreshToken)
                .signWith(getSignInKey(JwtConstant.REFRESH_TOKEN_SECRET), SignatureAlgorithm.HS256)
                .compact();
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return new AuthResponseDTO(accessToken, userConverter.toDtoAfterLogin((entity)));
    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        if (validateToken(refreshToken, JwtConstant.REFRESH_TOKEN_SECRET)) {
            String username = getUsernameFromJwt(refreshToken, JwtConstant.REFRESH_TOKEN_SECRET);
            UserEntity entity = userRepository.findOneByUsername(username);
            Date currentDate = new Date(System.currentTimeMillis());
            Date expiredDate = new Date(currentDate.getTime() + JwtConstant.JWT_EXPIRATION);
            String accessToken = Jwts.builder()
                    .setClaims(Map.of("role", entity.getRoles(), "name", username))
                    .setIssuedAt(new Date())
                    .setExpiration(expiredDate)
                    .signWith(getSignInKey(JwtConstant.JWT_SECRET), SignatureAlgorithm.HS256)
                    .compact();
            return new AuthResponseDTO(accessToken, userConverter.toDtoAfterLogin((entity)));
        } else {
            throw new AuthenticationCredentialsNotFoundException("Refresh token was expired or incorrect");
        }

    }

    public String getUsernameFromJwt(String token, String secretKey) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return String.valueOf(claims.get("name"));
    }

    public boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    private Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void resetCookie(){
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setMaxAge(0);
        httpServletResponse.addCookie(deleteCookie);
    }
}
