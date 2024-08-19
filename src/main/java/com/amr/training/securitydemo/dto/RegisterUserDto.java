package com.amr.training.securitydemo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
}
