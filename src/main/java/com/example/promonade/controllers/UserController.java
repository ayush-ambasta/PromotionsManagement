package com.example.promonade.controllers;

import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String headerAuth){
        return ResponseEntity.ok(userService.deleteUser(username,headerAuth));
    }

    @GetMapping("/team")
    public ResponseEntity<?>getAllUserFromTeam(@RequestParam("name") Team name) {
        return ResponseEntity.ok(userService.getAllUserFromTeam(name));
    }
}
