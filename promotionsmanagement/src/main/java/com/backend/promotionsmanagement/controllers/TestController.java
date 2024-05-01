package com.backend.promotionsmanagement.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('MANAGER') or hasRole('OWNER')")
    public String userAccess() {
        return "User Content.";
    }


    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public String ownerAccess() {
        return "Owner Content.";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String managerAccess() {
        return "manager Content.";
    }

}
