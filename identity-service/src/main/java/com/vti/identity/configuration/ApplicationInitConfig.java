package com.vti.identity.configuration;

import java.util.HashSet;

import com.vti.identity.dto.request.UserCreationRequest;
import com.vti.identity.mapper.ProfileMapper;
import com.vti.identity.repository.httpClient.ProfileClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vti.identity.constant.PredefinedRole;
import com.vti.identity.entity.Role;
import com.vti.identity.entity.User;
import com.vti.identity.repository.RoleRepository;
import com.vti.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    ProfileClient profileClient;
    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();
                UserCreationRequest request = UserCreationRequest.builder()
                        .city("Hanoi")
                        .dob(java.time.LocalDate.of(1999, 1, 1))
                        .firstName("Admin")
                        .lastName("Admin")
                        .email(ADMIN_EMAIL)
                        .password(ADMIN_PASSWORD)
                        .username(ADMIN_USER_NAME)
                        .build();

                userRepository.save(user);

                var profileRequest = profileMapper.toProfileCreationRequest(request);
                profileRequest.setUserId(user.getId());
                profileClient.createProfile(profileRequest);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
