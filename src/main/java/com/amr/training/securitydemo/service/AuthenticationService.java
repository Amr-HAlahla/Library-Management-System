package com.amr.training.securitydemo.service;

import com.amr.training.securitydemo.dto.LoginUserDto;
import com.amr.training.securitydemo.dto.RegisterUserDto;
import com.amr.training.securitydemo.entity.Role;
import com.amr.training.securitydemo.entity.RoleEnum;
import com.amr.training.securitydemo.entity.User;
import com.amr.training.securitydemo.repository.RoleRepository;
import com.amr.training.securitydemo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public User signup(RegisterUserDto input) {
        Optional<Role> role = roleRepository.findByName(RoleEnum.USER);
        if (role.isEmpty()) {
            return null;
        }
        User user = User
                .builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(role.get())
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}