package com.products.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.products.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UsuarioRepository usuarioRepository;
    

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> usuarioRepository.findByCorreo(username)
            .orElseThrow(()->new UsernameNotFoundException("El usuario no se ha encontrado."));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEnconder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    // @Bean
    // public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
    //     FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
    //     registrationBean.setFilter(new CorsFilter());
    //     registrationBean.addUrlPatterns("/*"); // Aplicar el filtro a todas las URL
    //     registrationBean.setOrder(1); // Orden del filtro
    //     return registrationBean;
    // }
}
