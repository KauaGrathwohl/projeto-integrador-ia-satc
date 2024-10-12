package com.nutrisys.api.security.customtoken;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private final Object principal;
    private final Long entidade;
    private final String token;
    private final Long idUsuario;

    public CustomAuthenticationToken(Object principal,
                                     Long entidade,
                                     String token,
                                     Long idUsuario,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.entidade = entidade;
        this.token = token;
        this.idUsuario = idUsuario;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Long getEntidade() {
        return this.entidade;
    }

    public String getToken() {
        return this.token;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }
}