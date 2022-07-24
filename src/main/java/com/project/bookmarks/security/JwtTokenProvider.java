package com.project.bookmarks.security;

import com.project.bookmarks.exception.CustomException;
import com.project.bookmarks.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private MyUserDetails myUserDetails;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String appUserRoles) {
        log.info("creating token for username: {}", username);
        //initialize JWT claims with username (email) as subject
        Claims claims = Jwts.claims().setSubject(username);
        //put user roles as auth attribute into claims
        claims.put("auth", appUserRoles);

        //set token creation & expiration datetime (1h from token created)
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        //generate and return JWT token
        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        //get token part from request Authorization header
        String bearerToken = req.getHeader("Authorization");
        log.info("resolving token: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.info("token resolved: {}",bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        //TODO: do token validation using signing key
        try {
            log.info("validating token: {}", token);
            //validate token using provided secretKey from application.properties
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            log.info("Got valid token!");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            //if token validation failed, throw new validation error exception
            log.error("Expired or invalid JWT token");
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
