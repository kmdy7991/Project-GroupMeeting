package com.groupmeeting.dto.request.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final Long id;
    private final String userNickname;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String userNickname, boolean active, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userNickname = userNickname;
        this.active = active;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userNickname;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
