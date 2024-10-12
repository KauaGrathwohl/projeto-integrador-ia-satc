package com.nutrisys.api.paciente;

import com.nutrisys.api.enums.StatusPaciente;
import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Paciente;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.paciente.dto.CreatePacienteDto;
import com.nutrisys.api.paciente.dto.CreatedPacienteDto;
import com.nutrisys.api.paciente.dto.ListPacienteDto;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.PacienteRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntidadeRepository entidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CreatedPacienteDto createPaciente(CreatePacienteDto createPacienteDto) {
        Paciente pacienteCreated = pacienteRepository.save(createUser(createPacienteDto));
        return new CreatedPacienteDto(pacienteCreated.getId(),
                pacienteCreated.getNome(),
                pacienteCreated.getCpf(),
                pacienteCreated.getDtNascimento(),
                pacienteCreated.getPeso(),
                pacienteCreated.getAltura(),
                pacienteCreated.getObjetivo(),
                pacienteCreated.getRestricoes(),
                pacienteCreated.getPreferencias(),
                pacienteCreated.getDhCriacao());
    }

    private Paciente createUser(CreatePacienteDto createPacienteDto) {
        Entidade entidade = entidadeRepository.findById(authenticationFacade.getAuthentication().getEntidade()).get();
        Usuario usuario = usuarioRepository.findById(authenticationFacade.getAuthentication().getIdUsuario()).get();
        return Paciente.builder()
                .usuario(usuario)
                .entidade(entidade)
                .nome(createPacienteDto.nome())
                .cpf(createPacienteDto.cpf())
                .dtNascimento(createPacienteDto.dtNascimento())
                .status(StatusPaciente.ATIVO)
                .peso(createPacienteDto.peso())
                .altura(createPacienteDto.altura())
                .objetivo(createPacienteDto.objetivo())
                .restricoes(createPacienteDto.restricoes())
                .preferencias(createPacienteDto.preferencias())
                .dhCriacao(LocalDateTime.now()).build();
    }

    public List<ListPacienteDto> listPacienteDtos() {
        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        Long usuario = authenticationFacade.getAuthentication().getIdUsuario();
        return pacienteRepository.findByEntidadeAndUsuario(entidade, usuario);
    }
}