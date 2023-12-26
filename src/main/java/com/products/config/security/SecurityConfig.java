package com.products.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // habilita la seguridad a nivel de metodos
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(csrf->{
                csrf.disable();
            })
            .authorizeHttpRequests(auth->{
                auth.requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST ,"/api/v1/file/imagen").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/producto").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/pedido").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/categorias").permitAll()
                .requestMatchers("/docs.html").permitAll()
                .anyRequest().authenticated();
            })
            .sessionManagement(session->{
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
