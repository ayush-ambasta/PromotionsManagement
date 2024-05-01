package com.backend.promotionsmanagement.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Integer id;
    private String username;
    private String email;
    @ToString.Exclude
    private List<String> roles;

    public JwtResponse(String accessToken, Integer id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}