package com.nadine.books.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt == null || !jwt.startsWith(SecParams.PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = jwt.substring(SecParams.PREFIX.length());
        try {
            JWT.require(Algorithm.HMAC256(SecParams.SECRET)).build().verify(token);
            String username = JWT.decode(token).getSubject();
            List<String> roles = JWT.decode(token).getClaim("roles").asList(String.class);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (roles != null) {
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
