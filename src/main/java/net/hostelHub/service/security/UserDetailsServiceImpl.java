package net.hostelHub.service.security;

import lombok.RequiredArgsConstructor;
import net.hostelHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        var user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .map(UserRegistrationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        net.hostelHub.entity.User user =
//                userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                        .orElseThrow(() -> new UsernameNotFoundException("Details not found for this USER : " + usernameOrEmail));
//
        return new User(usernameOrEmail, user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRole()))));

    }

}
