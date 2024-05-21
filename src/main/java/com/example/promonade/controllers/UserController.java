package com.example.promonade.controllers;

import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('MANAGER')")
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username,
                                        @RequestHeader("Authorization") String headerAuth){
        return ResponseEntity.ok(userService.deleteUser(username,headerAuth));
    }


    @GetMapping("/current-team-users")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('MANAGER')")
    public ResponseEntity<?>getAllUserFromTeam(@RequestHeader("Authorization") String headerAuth) {
        return ResponseEntity.ok(userService.getUsersFromUserTeam(headerAuth));
    }

//    below endpoints are for development purpose

    @DeleteMapping("/delete-noauth/{username}")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('MANAGER')")
    public ResponseEntity<?> deleteUserNoAuth(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.deleteUserNoAuth(username));
    }


    @GetMapping("/team-users")
    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('MANAGER')")
    public ResponseEntity<?>getAllUserFromTeam(@RequestParam("team") Team team) {
        return ResponseEntity.ok(userService.getAllUserFromTeam(team));

    }
}
