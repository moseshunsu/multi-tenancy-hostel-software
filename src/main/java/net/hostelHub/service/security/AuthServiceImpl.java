package net.hostelHub.service.security;

import net.hostelHub.dto.AuthResponse;
import net.hostelHub.dto.LoginDto;
import net.hostelHub.filter.JwtTokenProvider;
import net.hostelHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtTokenProvider.generateToken(authentication));

//        String controllerNumber =
//                userRepository.findByEmailOrUsername(loginDto.getUsername(), loginDto.getUsername()).get().getControllerNumber();
        String email =
                userRepository.findByUsernameOrEmail(loginDto.getUsername(),loginDto.getUsername()).get().getEmail();
//        authResponse.setControllerNumber(controllerNumber);
        authResponse.setEmail(email);

        return authResponse;
    }

}
