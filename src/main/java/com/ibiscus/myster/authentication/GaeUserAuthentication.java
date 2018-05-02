package com.ibiscus.myster.authentication;

import com.ibiscus.myster.service.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GaeUserAuthentication implements Authentication {

    private final UserInfo userInfo;

    public GaeUserAuthentication(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userInfo.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userInfo.getPassword();
    }

    @Override
    public Object getDetails() {
        return userInfo;
    }

    @Override
    public Object getPrincipal() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
