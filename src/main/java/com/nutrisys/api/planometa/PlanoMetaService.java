package com.nutrisys.api.planometa;

import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.PlanoMeta;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.PlanoMetaRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public CreatedPlanoMetaDto createPlanoMeta(CreatePlanoMetaDto createPlanoMetaDto) {
        PlanoMeta planoMeta = createEntity(createPlanoMetaDto);
        PlanoMeta planoMetaCreated = planoMetaRepository.save(planoMeta);
        return new CreatedPlanoMetaDto(planoMetaCreated.getId(),
                planoMetaCreated.getDtInicioMeta(),
                planoMetaCreated.getQtdDiariaCalorias(),
                planoMetaCreated.getQtdDiariaCarboidratos(),
                planoMetaCreated.getQtdDiariaGordura(),
                planoMetaCreated.getQtdDiariaProteinas(),
                planoMetaCreated.getDhCriacao());
    }

    private PlanoMeta createEntity(CreatePlanoMetaDto createPlanoMetaDto) {
        Entidade entidade = entidadeRepository.findById(authenticationFacade.getAuthentication().getEntidade()).get();
        Usuario usuario = usuarioRepository.findById(authenticationFacade.getAuthentication().getIdUsuario()).get();
        return PlanoMeta.builder()
                .usuario(usuario)
                .entidade(entidade)
                .dtInicioMeta(createPlanoMetaDto.dtInicial())
                .qtdDiariaCalorias(createPlanoMetaDto.qtdDiariaCalorias())
                .qtdDiariaCarboidratos(createPlanoMetaDto.qtdDiariaCarboidratos())
                .qtdDiariaGordura(createPlanoMetaDto.qtdDiariaGordura())
                .qtdDiariaProteinas(createPlanoMetaDto.qtdDiariaProteina())
                .dhCriacao(LocalDateTime.now())
                .build();
    }
}