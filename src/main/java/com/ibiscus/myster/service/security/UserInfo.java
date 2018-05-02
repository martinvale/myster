package com.ibiscus.myster.service.security;

import com.google.common.collect.ImmutableList;
import com.ibiscus.myster.model.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserInfo implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String username;
    private final String password;
    private final boolean enabled;

    public UserInfo(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ImmutableList.of(new SimpleGrantedAuthority("ROLE_SHOPPER"));
    }

    public long getUserId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
        return enabled;
    }
}
