package com.nutrisys.api.security.contextprovider;

import com.nutrisys.api.security.customtoken.CustomAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public CustomAuthenticationToken getAuthentication() {
        return (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }
}