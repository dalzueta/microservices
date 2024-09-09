package org.daniel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
  AuthenticationFilter authenticationFilter;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF si es necesario
        .authorizeRequests(authz -> authz
            .requestMatchers("/rutas-q-no-necesitan-autenticacion").permitAll()
            .anyRequest().authenticated() // todas las demás si necesitan autenticación
        )
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agrega el filtro personalizado
    return http.build();
  }

}



//  @Bean
//  public UserDetailsService userDetailsService() {
//    UserDetails user = User.withDefaultPasswordEncoder()
//        .username("user")
//        .password("password")
//        .roles("USER")
//        .build();
//
//    return new InMemoryUserDetailsManager(user);
//  }