package com.money.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.money.management.service.CustomOAuth2UserService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final CustomOAuth2UserService customOAuth2UserService;
    
    /****
     * Constructs a SecurityConfig instance with the specified custom OAuth2 user service.
     *
     * @param customOAuth2UserService the service used to retrieve user information during OAuth2 authentication
     */
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }
    
    /**
     * Configures and returns the application's security filter chain.
     *
     * Sets up CORS, disables CSRF protection, and defines authorization rules to permit public access to specific endpoints while requiring authentication for all others. Configures OAuth2 login with a custom login page, a default success redirect, and a custom user service for user information retrieval. Enables "remember me" functionality with a specified key and one-day token validity.
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/login**", "/error**", "/oauth2/authorization/google").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("${spring.security.login.success-url}", true)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
            .and()
                .rememberMe()
                    .key("uniqueAndSecret")
                    .tokenValiditySeconds(86400);
        
        return http.build();
    }
    
    /**
     * Creates and configures a CORS policy allowing requests from "http://localhost:8080" with credentials, supporting GET, POST, PUT, and DELETE methods for all endpoints.
     *
     * @return a CorsConfigurationSource with the specified CORS settings applied to all URL paths
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}