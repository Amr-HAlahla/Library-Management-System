package com.amr.training.securitydemo.bootstrap;

import com.amr.training.securitydemo.dto.RegisterUserDto;
import com.amr.training.securitydemo.entity.Role;
import com.amr.training.securitydemo.entity.RoleEnum;
import com.amr.training.securitydemo.entity.User;
import com.amr.training.securitydemo.repository.RoleRepository;
import com.amr.training.securitydemo.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = RegisterUserDto.builder()
                .fullName("Super Admin")
                .email("super.admin@email.com")
                .password("123456")
                .build();

        // Fetch the SUPER_ADMIN role
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);

        // If the SUPER_ADMIN role doesn't exist, exit the method
        if (optionalRole.isEmpty()) {
            throw new IllegalStateException("SUPER_ADMIN role not found. Please ensure roles are properly seeded.");
        }

        // Check if the SUPER_ADMIN user already exists
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isEmpty()) {
            // Create and save the SUPER_ADMIN user if it doesn't exist
            User user = User.builder()
                    .fullName(userDto.getFullName())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(optionalRole.get())
                    .build();

            userRepository.save(user);
        }
    }
}
