package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter; // ✅ हा import बदला
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // ✅ हा बदल

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rooms").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rooms/**")
                    .hasAnyRole("ADMIN","MANAGER","RECEPTIONIST")
                .requestMatchers(HttpMethod.POST, "/api/rooms/**")
                    .hasRole("ADMIN")
                .requestMatchers("/api/bookings/reception/**")
                    .hasAnyRole("ADMIN","RECEPTIONIST")
                .requestMatchers(HttpMethod.GET, "/api/bookings/**")
                    .hasAnyRole("ADMIN","MANAGER","RECEPTIONIST")
                .requestMatchers(HttpMethod.PUT, "/api/bookings/**")
                    .hasAnyRole("ADMIN","MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/bookings/**")
                    .hasAnyRole("ADMIN","MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/rooms/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/rooms/**")
                    .hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, // ✅ हा बदल
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}