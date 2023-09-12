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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static net.hostelHub.utils.Role.MANAGER;
import static net.hostelHub.utils.Role.OCCUPANT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests( requests -> requests
                        .requestMatchers("api/v1/users/**").permitAll()
                        .requestMatchers("api/v1/verify/**").permitAll()
                        .requestMatchers("api/v1/tenants/**").permitAll()
                        .requestMatchers("api/v1/rooms/**").permitAll()
                        .requestMatchers("api/v1/bookings/**").permitAll()
                        .requestMatchers("api/v1/demo").hasAnyRole(OCCUPANT.name(), MANAGER.name()))
                        .formLogin(Customizer.withDefaults())
                        .httpBasic(Customizer.withDefaults());

        http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}
