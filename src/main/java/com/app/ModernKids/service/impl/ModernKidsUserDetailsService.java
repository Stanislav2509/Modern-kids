package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.entity.UserEntity;
import com.app.ModernKids.model.entity.UserRole;
import com.app.ModernKids.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ModernKidsUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ModernKidsUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).
                map(this::mapUserToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private UserDetails mapUserToUserDetails(UserEntity userEntity){
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(ModernKidsUserDetailsService::map).toList())
                .build();
    }
    private static GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRole.getRole().name()
        );
    }
}
