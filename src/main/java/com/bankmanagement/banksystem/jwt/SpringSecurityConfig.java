package com.bankmanagement.banksystem.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers(HttpMethod.GET, "/").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/log-in").permitAll();
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/sign-up").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/main-page/list-banks").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/main-page/loans-history").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/main-page/bank/{bankId}").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/bank/{bankId}/transfer").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/bank/{bankId}/deposit").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/bank/{bankId}/withdraw").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/bank/{bankId}/loans").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/main-page/user/{userId}").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/change-password").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/main-page/search").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/admin-delete").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/admin-change").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/main-page/admin-create").permitAll();
            authorize.anyRequest().authenticated();
        }).httpBasic(org.springframework.security.config.Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); 
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
