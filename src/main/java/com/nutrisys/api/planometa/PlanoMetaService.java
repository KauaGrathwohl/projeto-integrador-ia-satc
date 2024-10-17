package com.nutrisys.api.planometa;

import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Paciente;
import com.nutrisys.api.model.PlanoMeta;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.paciente.dto.ListPacienteDto;
import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.PacienteRepository;
import com.nutrisys.api.repository.PlanoMetaRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoMetaService {

    @Autowired
    private PlanoMetaRepository planoMetaRepository;

    @Autowired
    private EntidadeRepository entidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private PacienteRepository pacienteRepository;

    public CreatedPlanoMetaDto createPlanoMeta(CreatePlanoMetaDto createPlanoMetaDto, Long idPaciente) {
        Optional<Paciente> pacienteIptional = pacienteRepository.findById(idPaciente);
        if (pacienteIptional.isEmpty()) {
            throw new RuntimeException("Paciente informado não foi encontrado");
        }

        PlanoMeta planoMeta = createEntity(createPlanoMetaDto, pacienteIptional.get());
        PlanoMeta planoMetaCreated = planoMetaRepository.save(planoMeta);
        return new CreatedPlanoMetaDto(planoMetaCreated.getId(),
                pacienteIptional.get().getId(),
                planoMetaCreated.getNomePlano(),
                planoMetaCreated.getDtInicioMeta(),
                planoMetaCreated.getQtdDiariaCalorias(),
                planoMetaCreated.getQtdDiariaCarboidratos(),
                planoMetaCreated.getQtdDiariaGordura(),
                planoMetaCreated.getQtdDiariaProteinas(),
                planoMetaCreated.getDhCriacao());
    }

    private PlanoMeta createEntity(CreatePlanoMetaDto createPlanoMetaDto, Paciente paciente) {
        Entidade entidade = entidadeRepository.findById(authenticationFacade.getAuthentication().getEntidade()).get();
        Usuario usuario = usuarioRepository.findById(authenticationFacade.getAuthentication().getIdUsuario()).get();
        return PlanoMeta.builder()
                .usuario(usuario)
                .entidade(entidade)
                .paciente(paciente)
                .nomePlano(createPlanoMetaDto.nomePlano())
                .dtInicioMeta(createPlanoMetaDto.dtInicial())
                .qtdDiariaCalorias(createPlanoMetaDto.qtdDiariaCalorias())
                .qtdDiariaCarboidratos(createPlanoMetaDto.qtdDiariaCarboidratos())
                .qtdDiariaGordura(createPlanoMetaDto.qtdDiariaGordura())
                .qtdDiariaProteinas(createPlanoMetaDto.qtdDiariaProteina())
                .dhCriacao(LocalDateTime.now())
                .build();
    }

    public List<ListPlanoMetaDto> listPlanosMeta(Long idPaciente) {
        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        Long usuario = authenticationFacade.getAuthentication().getIdUsuario();
        return planoMetaRepository.findByEntidadeAndUsuarioAndPaciente(entidade, usuario, idPaciente);
    }
}