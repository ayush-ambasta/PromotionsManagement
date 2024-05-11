package com.example.promonade.dto.response.userdtos;

import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String accessToken;
    String tokenType = "Bearer";
    Integer id;
    String username;
    String email;
    ERole role;

    public JwtResponse(String accessToken, Integer id, String username, String email, String role) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role.equals("OWNER")?ERole.OWNER: ERole.MANAGER;
    }
}
