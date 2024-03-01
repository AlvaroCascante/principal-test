package com.example.principaltest.integration;

import com.example.principaltest.services.JpaUserDetailsService;
import com.example.principaltest.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private final JpaUserDetailsService userDetailsService;

    private final TokenService tokenService;

    @Autowired
    public WithMockCustomUserSecurityContextFactory(JpaUserDetailsService userDetailsService, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    public SecurityContext createSecurityContext(WithMockCustomUser withUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String username = withUser.username();
        Assert.hasLength(username, "value() must be non-empty String");
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());

        // Token service takes an authentication (user/pass) as input to create the token
        String token = tokenService.generateToken(authentication);

        // Setting the authentication with the generated token
        Authentication tokenAuthentication = new PreAuthenticatedAuthenticationToken(principal, token, principal.getAuthorities());
        context.setAuthentication(tokenAuthentication);
        return context;
    }
}