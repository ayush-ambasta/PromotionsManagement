package com.example.promonade.dto.request.userdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}