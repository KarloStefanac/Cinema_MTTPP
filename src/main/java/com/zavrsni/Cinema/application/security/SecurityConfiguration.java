package com.zavrsni.Cinema.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/register/**").permitAll();
                    registry.requestMatchers("/home", "/register/**").permitAll();
                    registry.requestMatchers("/movie").hasRole("ADMIN");
                    registry.requestMatchers("/movie/*").permitAll();
                    registry.requestMatchers("/movie/save").hasRole("ADMIN");
                    registry.requestMatchers("/screening/**").hasRole("USER");
                    registry.requestMatchers("/screening/save").hasRole("ADMIN");
                    registry.requestMatchers("movie/*/screening").hasRole("ADMIN");
                    registry.requestMatchers("halls/**").hasRole("ADMIN");
                    registry.requestMatchers("/register/**").permitAll();
                    registry.requestMatchers("/ticket/**").permitAll();
                    registry.requestMatchers("/profile").hasRole("USER");
                    registry.anyRequest().permitAll();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
