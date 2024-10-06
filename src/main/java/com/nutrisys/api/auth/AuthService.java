package com.nutrisys.api.auth;

import com.nutrisys.api.auth.dto.CreateUserDto;
import com.nutrisys.api.auth.dto.LoginUserDto;
import com.nutrisys.api.auth.dto.RecoveryJwtTokenDto;
import com.nutrisys.api.configuration.SecurityConfiguration;
import com.nutrisys.api.enums.StatusUsuario;
import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Role;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.JwtTokenService;
import com.nutrisys.api.security.userdetailimp.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntidadeRepository entidadeRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;


    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.usuario(), loginUserDto.senha());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {
        Optional<Entidade> entidade = entidadeRepository.findById(createUserDto.idEntidade());
        if (entidade.isEmpty()) {
            throw new RuntimeException("Entidade informada não existe");
        }

        Usuario newUser = Usuario.builder()
                .usuario(createUserDto.usuario())
                .entidades(Set.of(entidade.get()))
                .senha(securityConfiguration.passwordEncoder().encode(createUserDto.senha()))
                .nome(createUserDto.nome())
                .dtNascimento(createUserDto.dtNascimento())
                .crn(createUserDto.crn())
                .cpfCnpj(createUserDto.cpfCnpj())
                .dhCriacao(LocalDateTime.now())
                .status(StatusUsuario.ATIVO)
                .roles(List.of(Role.builder().roleName(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        usuarioRepository.save(newUser);
    }
}