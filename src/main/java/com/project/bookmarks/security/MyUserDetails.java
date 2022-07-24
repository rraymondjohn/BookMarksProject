package com.project.bookmarks.security;
import com.project.bookmarks.model.User;
import com.project.bookmarks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO: load user detail from repository and return as org.springframework.security.core.userdetails.User
        //try to load user record from repository by username provided (email)
        final User user = userRepository.findByUsername(username);
        //check if user found, otherwise send UsernameNotFoundException exception

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        //build and return UserDetails object from username (email), password, and authorities (roles)
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.getPassword())//
                .authorities(user.getRole())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
