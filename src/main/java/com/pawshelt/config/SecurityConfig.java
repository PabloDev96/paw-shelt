package com.pawshelt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    // ⬇️ Bean para hashear y verificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ⬇️ Configuración de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers(headers -> headers
                        .frameOptions().sameOrigin() // 👈 ESTA LÍNEA PERMITE IFRAME PARA H2
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/usuarios/**", "/auth/register", "/auth/login").permitAll() // públicos
                        .requestMatchers("/animales/**").authenticated() // requieren login
                        .anyRequest().denyAll()
                )

                .formLogin().disable()
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
