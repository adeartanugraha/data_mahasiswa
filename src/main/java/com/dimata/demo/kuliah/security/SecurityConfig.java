package com.dimata.demo.kuliah.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http.csrf().disable()
        .formLogin().disable()
        .logout().disable()
        .authorizeExchange()
        .pathMatchers("/","/maintainer/v2/**").permitAll()
        .anyExchange().denyAll()
        .and().build();
    }
   
    MapReactiveUserDetailsService userDetailsService(){
        UserDetails rob = User.builder()
        .username("bagus@gmail.com")
        .password("{noop}password")
        .roles("USER")
        .build();
        return new MapReactiveUserDetailsService(rob);
    }
}
