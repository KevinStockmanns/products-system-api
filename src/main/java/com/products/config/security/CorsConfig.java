package com.products.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir CORS para todas las rutas
            .allowedOrigins("http://localhost:4200") // Permitir solicitudes desde cualquier origen
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos permitidos
            .allowedHeaders("*") // Permitir todos los encabezados
            .allowCredentials(true); // Permitir credenciales (cookies, autenticación)
    }
    
}
