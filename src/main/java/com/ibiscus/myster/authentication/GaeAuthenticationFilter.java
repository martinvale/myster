package com.ibiscus.myster.authentication;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GaeAuthenticationFilter extends GenericFilterBean {

    private AuthenticationDetailsSource ads = new WebAuthenticationDetailsSource();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    private final AuthenticationManager authenticationManager;

    public GaeAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            // User isn't authenticated. Check if there is a Google Accounts user
            User googleUser = UserServiceFactory.getUserService().getCurrentUser();

            if (googleUser != null) {
                // User has returned after authenticating through GAE. Need to authenticate to Spring Security.
                PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
                token.setDetails(ads.buildDetails(request));

                try {
                    authentication = authenticationManager.authenticate(token);
                    // Setup the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // Send new users to the registration page.
                    /*if (authentication.getAuthorities().contains(AppRole.NEW_USER)) {
                        ((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);
                        return;
                    }*/
                } catch (AuthenticationException e) {
                    // Authentication information was rejected by the authentication manager
                    failureHandler.onAuthenticationFailure((HttpServletRequest)request, (HttpServletResponse)response, e);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

}
