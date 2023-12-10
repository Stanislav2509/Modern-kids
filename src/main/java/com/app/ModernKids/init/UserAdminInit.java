package com.app.ModernKids.init;

import com.app.ModernKids.model.entity.UserEntity;
import com.app.ModernKids.model.entity.UserRole;
import com.app.ModernKids.model.enums.UserRoleEnum;
import com.app.ModernKids.repo.UserRepository;
import com.app.ModernKids.repo.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserAdminInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAdminInit(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        List<UserEntity> users = userRepository.findAll();
        boolean isExistAdmin = false;

        if(users.isEmpty()){
            creatAdmin();
        } else{
            for (UserEntity user : users) {
                Set<UserRole> roles = user.getRoles();
                if(roles.size() > 1){
                    isExistAdmin = true;
                    break;
               }
            }

            if(!isExistAdmin){
                creatAdmin();
            }
        }
    }

    private void creatAdmin(){
        UserEntity user = new UserEntity();
        user.setFirstName("Ivan");
        user.setLastName("Cenkov");
        user.setEmail("ivan@gmail.com1");
        user.setPhoneNumber("08782501081");
        user.setAddress("z.k. Mladost");
        user.setPassword(passwordEncoder.encode("123456"));
        UserRole userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        UserRole adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        Set<UserRole> roles = Set.of(userRole, adminRole);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
