package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.UserRegisterBindingModel;
import com.app.ModernKids.model.entity.UserEntity;
import com.app.ModernKids.model.entity.UserRole;
import com.app.ModernKids.model.enums.UserRoleEnum;
import com.app.ModernKids.repo.UserRepository;
import com.app.ModernKids.repo.UserRoleRepository;
import com.app.ModernKids.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean register(UserRegisterBindingModel userRegisterBindingModel) {
        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            return false;
        }

        Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(userRegisterBindingModel.getPhoneNumber());

        if(userOptional.isPresent()){
            return false;
        }

        UserEntity user = new UserEntity();
        user.setFirstName(userRegisterBindingModel.getFirstName());
        user.setLastName(userRegisterBindingModel.getLastName());
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user.setAddress(userRegisterBindingModel.getAddress());
        user.setPhoneNumber(userRegisterBindingModel.getPhoneNumber());
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        Set<UserRole> roles = Set.of(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return true;
    }
}
