package net.hostelHub.config;

import lombok.RequiredArgsConstructor;
import net.hostelHub.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static net.hostelHub.utils.Role.MANAGER;
import static net.hostelHub.utils.Role.OCCUPANT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests( requests -> requests
                        .requestMatchers("api/v1/users/change-password")
                        .authenticated()
                        .requestMatchers(
                                "api/v1/rooms/room-types",
                                "api/v1/rooms/room")
                        .hasAnyRole(MANAGER.name())
                        .requestMatchers(
                                "api/v1/bookings/**",
                                "api/v1/rooms/**",
                                "api/v1/properties/hostels")
                        .hasAnyRole(OCCUPANT.name(), MANAGER.name())
                        .requestMatchers(
                                "api/v1/payment/**",
                                "api/v1/users/**",
                                "api/v1/auth/**",
                                "api/v1/demo",
                                "api/v1/verify/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers(
                                "api/v1/properties/**",
                                "api/v1/rooms/**")
                        .hasRole(MANAGER.name()))
                        .formLogin(Customizer.withDefaults())
                        .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                );

        return http.build();
    }

}
