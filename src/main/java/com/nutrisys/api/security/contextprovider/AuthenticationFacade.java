package com.nutrisys.api.security.contextprovider;

import com.nutrisys.api.security.customtoken.CustomAuthenticationToken;

public interface AuthenticationFacade {
    CustomAuthenticationToken getAuthentication();
}