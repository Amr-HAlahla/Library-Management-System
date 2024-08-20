package com.amr.training.library.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private String email;
    private String password;
}
