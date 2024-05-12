package com.example.promonade.service;

import com.example.promonade.dto.request.userdtos.LoginRequest;
import com.example.promonade.dto.request.userdtos.SignupRequest;
import com.example.promonade.dto.response.UpdationResponse;
import com.example.promonade.dto.response.userdtos.JwtResponse;
import com.example.promonade.dto.response.userdtos.SignupResponse;
import com.example.promonade.dto.response.userdtos.UserResponse;
import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;

import com.example.promonade.exceptions.userExceptions.*;

import com.example.promonade.models.User;
import com.example.promonade.repositories.UserRepository;
import com.example.promonade.security.jwt.JwtUtils;
import com.example.promonade.security.service.UserDetailsImpl;
import com.example.promonade.service.transformers.UserTransformer;
import com.example.promonade.service.utils.GeneralUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final GeneralUtils generalUtils;

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

        if (signUpRequest.getTeam() == null) {
            throw new TeamNotExistsException("Team doesn't exist!");
        }

        // Create new user's account
        User user = UserTransformer.SignupRequestToUser(signUpRequest, encoder);

        //saving UserEntity to the database
        userRepository.save(user);

        return UserTransformer.userToSignupResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : allUsers) {

            UserResponse userResponse = UserTransformer.UserToUserResponse(user);

            userResponses.add(userResponse);
        }

        return userResponses;
    }


    public UpdationResponse deleteUser(String username,String authToken)  {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with username " + username + " does not exist");
        }
        User user = userOptional.get();
        User owner = generalUtils.getUserFromAuthToken(authToken);
        String ownerTeam = owner.getTeam().toString();
        String userTeam = user.getTeam().toString();

        if(user.getRole().equals(ERole.OWNER)){

            throw new UserNotAuthorisedException("User cannot delete another Owner");

        }
        //when team does not match
        if(!userTeam.equals(ownerTeam)){
            throw new TeamNotAuthorisedException(String.format("The owner team %s cannot delete %s user team!", ownerTeam, userTeam));
        }
        userRepository.delete(user);

        return new UpdationResponse(String.format("Successfully deleted user %s", username), true);

    }

    public List<UserResponse> getAllUserFromTeam(Team team) {

        List<User> allUsers = userRepository.findByTeam(team);

        return allUsers.stream()
                .map(UserTransformer::UserToUserResponse)
                .collect(Collectors.toList());
    }

    public UpdationResponse deleteUserNoAuth(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with username " + username + " does not exist");
        }
        User user = userOptional.get();
        userRepository.delete(user);
        return new UpdationResponse(String.format("Successfully deleted user %s", username), true);
    }

    public List<UserResponse> getUsersFromUserTeam(String headerAuth) {
        User user = generalUtils.getUserFromAuthToken(headerAuth);
        Team team = user.getTeam();

        List<User> allUsers = userRepository.findByTeam(team);

        return allUsers.stream()
                .map(UserTransformer::UserToUserResponse)
                .collect(Collectors.toList());

    }
}
