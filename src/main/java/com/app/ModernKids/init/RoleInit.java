package com.app.ModernKids.init;

import com.app.ModernKids.model.entity.UserRole;
import com.app.ModernKids.model.enums.UserRoleEnum;
import com.app.ModernKids.repo.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleInit implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;

    public RoleInit(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
       long count = userRoleRepository.count();

       if(count == 0) {
           List<UserRole> roles = new ArrayList<>();
           Arrays.stream(UserRoleEnum.values()).forEach(currRole -> {
               UserRole role= new UserRole();
               role.setRole(currRole);
               roles.add(role);
           });

           userRoleRepository.saveAll(roles);
       }
    }
}
