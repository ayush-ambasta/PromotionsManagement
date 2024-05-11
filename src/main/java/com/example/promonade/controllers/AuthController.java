package com.example.promonade.controllers;


import com.example.promonade.dto.request.userdtos.LoginRequest;
import com.example.promonade.dto.request.userdtos.SignupRequest;
import com.example.promonade.dto.response.MessageResponse;
import com.example.promonade.dto.response.userdtos.SignupResponse;
import com.example.promonade.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(userService.registerUser(signUpRequest));
    }
}
