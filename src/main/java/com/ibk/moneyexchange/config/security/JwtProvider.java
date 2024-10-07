package com.ibk.moneyexchange.config.security;

import com.ibk.moneyexchange.controller.handler.exceptions.UserAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    @Value("${auth-token.secret}")
    private String jwtSecret;
    @Value("${auth-token.expiration-milliseconds}")
    private int jwtExpiration;

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.split(" ")[1].trim();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
            throw new UserAuthenticationException(ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
            throw new UserAuthenticationException(ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
            throw new UserAuthenticationException(ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
            throw new UserAuthenticationException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty", ex);
            throw new UserAuthenticationException(ex);
        }
    }
}
