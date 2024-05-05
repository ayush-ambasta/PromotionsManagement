package com.example.promonade.dto.request.userdtos;

import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.userEnums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignupRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    String email;

    ERole role;
}
