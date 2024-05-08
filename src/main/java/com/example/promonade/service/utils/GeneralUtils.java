package com.example.promonade.service.utils;

import com.example.promonade.exceptions.userExceptions.UserNotFoundException;
import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GeneralUtils {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public User getUserFromAuthToken(String headerAuth){
        String authToken = headerAuth.substring(7, headerAuth.length());
        String username = jwtUtils.getUserNameFromJwtToken(authToken);
        System.out.println("Username: "+username);
        Optional<User> optionalCreatedBy = userRepository.findByUsername(username);
        if(optionalCreatedBy.isEmpty()){
            throw new UserNotFoundException(String.format("User with the specified username - %s is not found!", username));
        }
        return optionalCreatedBy.get();
    }
}
