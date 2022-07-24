package com.project.bookmarks.security;

import com.project.bookmarks.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //TODO: validate the token then set security auth context
        //using resolveToken from our jwtTokenProvider class, get token part from the request
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        log.info("security filter processing token: {}", token);
        try {
            //validating the token
            if (token != null && jwtTokenProvider.validateToken(token)) {
                //get Authentication object (UsernamePasswordAuthenticationToken)
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                log.info("found auth principal: {}", auth.getPrincipal());
                //set security context by current user Authentication
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex) {
            //clear security context when authentication is failed
            SecurityContextHolder.clearContext();
            log.error("security filter error: {}", ex.getMessage());
            //return error response
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
