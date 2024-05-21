package com.example.promonade.security.service;


import com.example.promonade.enums.userEnums.Team;
import com.example.promonade.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String email;

    @JsonIgnore
    private String password1;

    private Team team;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password1,
                           Collection<? extends GrantedAuthority> authorities,Team team) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.authorities = authorities;
        this.team=team;
    }

    public static UserDetailsImpl build(User user) {

        List<GrantedAuthority> authorities = new ArrayList<>(){{
            add(new SimpleGrantedAuthority(user.getRole().toString()));
        }};

        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword1(),
                authorities,
                user.getTeam());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password1;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public Team getTeam() {return team; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
