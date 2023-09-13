package net.hostelHub.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.security.AuthResponse;
import net.hostelHub.dto.security.LoginDto;
import net.hostelHub.entity.User;
import net.hostelHub.entity.token.Token;
import net.hostelHub.filter.JwtService;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.token.TokenRepository;
import net.hostelHub.utils.TokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        var user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .uniqueCode(user.getUniqueCode())
                .username(user.getUsername())
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String usernameOrUserEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return;
            }
            refreshToken = authHeader.substring(7);
            usernameOrUserEmail = jwtService.extractUsername(refreshToken);
            if (usernameOrUserEmail != null) {
                var user = this.userRepository.findByUsernameOrEmail(usernameOrUserEmail, usernameOrUserEmail)
                        .orElseThrow();
                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    savedUserToken(user, accessToken);
                    var authResponse = AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .uniqueCode(user.getUniqueCode())
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        }
    }

    private void savedUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
