package com.nutrisys.api.security;

import com.nutrisys.api.configuration.SecurityConfiguration;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.customtoken.CustomAuthenticationToken;
import com.nutrisys.api.security.userdetailimp.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UsuarioAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // Verifica se o endpoint requer autenticação antes de processar a requisição
        try {

            if (checkIfEndpointIsNotPublic(request)) {
                String token = recoveryToken(request); // Recupera o token do cabeçalho Authorization da requisição
                if (token != null) {
                    String subject = jwtTokenService.getSubjectFromToken(token); // Obtém o assunto (neste caso, o nome
                    // de usuário) do token
                    Long entidade = jwtTokenService.getEntidadeFromToken(token);
                    Usuario usuario = usuarioRepository.findByUsuario(subject).get(); // Busca o usuário pelo email (que
                    // é o assunto do token)
                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario, entidade, token); // Cria um UserDetails
                    // com o usuário
                    // encontrado

                    // Cria um objeto de autenticação do Spring Security
                    CustomAuthenticationToken authentication = new CustomAuthenticationToken(userDetails.getUsername(),
                        entidade,
                        token,
                        usuario.getId(),
                        userDetails.getAuthorities());
                    // Define o objeto de autenticação no contexto de segurança do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new RuntimeException("O token está ausente.");
                }
            }
            filterChain.doFilter(request, response); // Continua o processamento da requisição
        } catch (RuntimeException ex) {
            // Configura a resposta com o status 401 e a mensagem de erro
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"" + ex.getMessage() + "\"}");
        }
    }

    // Recupera o token do cabeçalho Authorization da requisição
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    // Verifica se o endpoint requer autenticação antes de processar a requisição
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }
}