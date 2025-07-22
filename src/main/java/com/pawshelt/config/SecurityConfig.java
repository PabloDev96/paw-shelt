package com.pawshelt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers(headers -> headers
                        .frameOptions().sameOrigin() // 👈 ESTA LÍNEA PERMITE IFRAME PARA H2
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/usuarios/**", "/login").permitAll() // públicos
                        .requestMatchers("/animales/**").authenticated() // requieren login
                        .anyRequest().denyAll()
                )

                .formLogin().disable();

        return http.build();
    }
}
