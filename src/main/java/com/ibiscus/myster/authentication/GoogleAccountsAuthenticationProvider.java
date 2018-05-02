package com.ibiscus.myster.authentication;

import com.google.appengine.api.users.User;
import com.ibiscus.myster.service.security.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public GoogleAccountsAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User googleUser = (User) authentication.getPrincipal();

        UserDetails user = userService.loadUserByUsername(googleUser.getEmail());

        if (!user.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
