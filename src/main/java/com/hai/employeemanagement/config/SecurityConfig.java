package com.hai.employeemanagement.config;

import com.hai.employeemanagement.jwt.JwtAuthEntryPoint;
import com.hai.employeemanagement.jwt.JwtAuthHandler;
import com.hai.employeemanagement.jwt.JwtAuthenticationFilter;
import com.hai.employeemanagement.service.CustomUserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private CustomUserDetailServiceImpl customUserDetailService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthHandler jwtAuthHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/lib/bootstrap/**", "/css/**", "/img/**", "/js/**","/","/login**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/"))
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(customUserDetailService)
                .exceptionHandling(exceptionHanding -> exceptionHanding
                        .accessDeniedHandler(this.jwtAuthHandler)
                        .authenticationEntryPoint(this.jwtAuthEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilters(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilters() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
