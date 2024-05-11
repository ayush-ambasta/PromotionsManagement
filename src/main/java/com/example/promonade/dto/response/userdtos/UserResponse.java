package com.example.promonade.dto.response.userdtos;

import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    String email;
    ERole role;
    Team team;
    String name;
}
