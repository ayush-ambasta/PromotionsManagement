package com.example.promonade.service;

import com.example.promonade.dto.request.userdtos.LoginRequest;
import com.example.promonade.dto.request.userdtos.SignupRequest;
import com.example.promonade.dto.response.userdtos.JwtResponse;
import com.example.promonade.dto.response.userdtos.SignupResponse;
import com.example.promonade.exceptions.userExceptions.RoleNotExistsException;
import com.example.promonade.exceptions.userExceptions.UserExistsException;
import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import com.example.promonade.security.service.UserDetailsImpl;
import com.example.promonade.service.transformers.UserTransformer;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles.get(0));
    }

    public SignupResponse registerUser(SignupRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserExistsException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserExistsException("Email is already in use!");
        }

        if (signUpRequest.getRole() == null) {
            throw new RoleNotExistsException("Role doesn't exist!");
        }

        // Create new user's account
        User user = UserTransformer.SignupRequestToUser(signUpRequest, encoder);

        //saving UserEntity to the database
        userRepository.save(user);

        return UserTransformer.userToSignupResponse(user);
    }
}
