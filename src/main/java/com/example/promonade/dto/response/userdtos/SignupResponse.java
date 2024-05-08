package com.example.promonade.dto.response.userdtos;

import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupResponse {
    Integer id;
    String username;
    String email;
    ERole role;
    Team team;
    String name;
    Date createdAt;
}
