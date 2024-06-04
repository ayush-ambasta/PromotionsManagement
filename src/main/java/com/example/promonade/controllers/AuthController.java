package com.example.promonade.controllers;

import com.example.promonade.dto.request.userdtos.LoginRequest;
import com.example.promonade.dto.request.userdtos.SignupRequest;
import com.example.promonade.dto.response.MessageResponse;
import com.example.promonade.dto.response.userdtos.SignupResponse;
import com.example.promonade.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
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

    @GetMapping("/test-token")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('MANAGER')")
    public ResponseEntity<?> testToken() {
        return ResponseEntity.ok(true);
    }
}
