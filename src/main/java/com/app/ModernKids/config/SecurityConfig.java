package com.app.ModernKids.config;

import com.app.ModernKids.model.enums.UserRoleEnum;
import com.app.ModernKids.repo.UserRepository;
import com.app.ModernKids.service.impl.ModernKidsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.headers.HttpStrictTransportSecurityDsl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizeRequest -> authorizeRequest
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/view-products/{categoryId}",
                                "/view-products/{categoryId}/{typeId}", "/curr-product/{productId}").permitAll()
                        .requestMatchers("/login", "/register", "/contact-form").permitAll()
                        .requestMatchers("/add-age", "/add-product", "/new-orders").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/courier-order/{id}", "/completed-order/{id}").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/failed-order/{id}", "/queries").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/queries{id}", "/add-type-product", "/add-collection").hasRole(UserRoleEnum.ADMIN.name())

                        .anyRequest().authenticated()
                ).formLogin(
                        formLogin ->{
                            formLogin
                                    .loginPage("/login")
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/",true)
                                    .failureForwardUrl("/login-error");

                        }
                ).logout(
                logout -> {
                    logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new ModernKidsUserDetailsService(userRepository);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
