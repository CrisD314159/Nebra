package com.crisdevApps.Nebra.security;

import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }

        return new UserDetailsImpl(userOptional.get());
    }
}
