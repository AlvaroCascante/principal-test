package com.example.principaltest.services;

import com.example.principaltest.models.SecurityUser;
import com.example.principaltest.repositories.LoginUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    private final LoginUserRepository userRepository;

    public JpaUserDetailsService(LoginUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(String.format("Username %s", username));
        return userRepository
                .findByUsernameIgnoreCase(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
