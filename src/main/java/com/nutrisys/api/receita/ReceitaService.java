package com.nutrisys.api.receita;

import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Receita;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.receita.dto.CreateReceitaDto;
import com.nutrisys.api.receita.dto.CreatedReceitaDto;
import com.nutrisys.api.receita.dto.ListReceitaDto;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.ReceitaRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntidadeRepository entidadeRepository;

    public CreatedReceitaDto createReceita(CreateReceitaDto createReceitaDto) {
        Receita receitaCreated = receitaRepository.save(createEntity(createReceitaDto));
        return new CreatedReceitaDto(receitaCreated.getId(),
                receitaCreated.getNome(),
                receitaCreated.getGramas(),
                receitaCreated.getCalorias(),
                receitaCreated.getProteinas(),
                receitaCreated.getCarboidratos(),
                receitaCreated.getGordura(),
                receitaCreated.getTipoRefeicao(),
                receitaCreated.getDescricao(),
                receitaCreated.getDhCriacao());
    }

    private Receita createEntity(CreateReceitaDto createReceitaDto) {
        Entidade entidade = entidadeRepository.findById(authenticationFacade.getAuthentication().getEntidade()).get();
        Usuario usuario = usuarioRepository.findById(authenticationFacade.getAuthentication().getIdUsuario()).get();
        return Receita.builder()
                .entidade(entidade)
                .usuario(usuario)
                .nome(createReceitaDto.nome())
                .gramas(createReceitaDto.gramas())
                .calorias(createReceitaDto.calorias())
                .proteinas(createReceitaDto.proteinas())
                .carboidratos(createReceitaDto.carboidratos())
                .gordura(createReceitaDto.gordura())
                .tipoRefeicao(createReceitaDto.tipoRefeicao())
                .descricao(createReceitaDto.descricao())
                .dhCriacao(LocalDateTime.now())
                .build();
    }

    public List<ListReceitaDto> listReceitaDtos(String filtro) {
        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        Long usuario = authenticationFacade.getAuthentication().getIdUsuario();
        return receitaRepository.findByEntidadeAndUsuarioAndFiltro(entidade, usuario, filtro);
    }

    public Long getQuantidadeReceitasPorEntidade() {
        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        return receitaRepository.countByEntidadeId(entidade);
    }
}