package com.example.promonade.service.transformers;

import com.example.promonade.dto.request.userdtos.SignupRequest;
import com.example.promonade.dto.response.userdtos.SignupResponse;
import com.example.promonade.dto.response.userdtos.UserResponse;
import com.example.promonade.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTransformer {

    public static User SignupRequestToUser(SignupRequest signupRequest, PasswordEncoder encoder){
        return User.builder()
                .username(signupRequest.getUsername())
                .password1(encoder.encode(signupRequest.getPassword()))
                .role(signupRequest.getRole())
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .team((signupRequest.getTeam()))
                .build();
    }

    public static SignupResponse userToSignupResponse(User user){
        return SignupResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getUserId())
                .role(user.getRole())
                .team(user.getTeam())
                .name(user.getName())
                .createdAt(user.getCreateAt())
                .build();
    }


    public static UserResponse UserToUserResponse(User user) {

        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .team(user.getTeam())
                .build();
    }
}
