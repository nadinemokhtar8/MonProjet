package com.nadine.books.security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cors = new CorsConfiguration();
                        cors.setAllowedOriginPatterns(Collections.singletonList("http://localhost:*"));
                        cors.setAllowedMethods(Collections.singletonList("*"));
                        cors.setAllowedHeaders(Collections.singletonList("*"));
                        cors.setExposedHeaders(Collections.singletonList("Authorization"));
                        return cors;
                    }
                }))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/all", "/api/all/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/getbyid/*", "/api/getbyid/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/addbook", "/api/addbook/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/updatebook", "/api/updatebook/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/delbook/*", "/api/delbook/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/genre", "/api/genre/**", "/api/image/**", "/api/booksgenre/**", "/api/booksByName/**")
                        .permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
