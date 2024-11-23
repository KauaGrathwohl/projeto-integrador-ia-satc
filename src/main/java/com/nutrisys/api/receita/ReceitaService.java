package com.nutrisys.api.receita;

import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Receita;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.receita.dto.CalculoReceitaDto;
import com.nutrisys.api.receita.dto.CreateReceitaDto;
import com.nutrisys.api.receita.dto.CreatedReceitaDto;
import com.nutrisys.api.receita.dto.RespostaCalculoReceitaDto;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.ReceitaRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    public CreatedReceitaDto createReceita(CreateReceitaDto createReceitaDto) {
        Receita receitaCreated = receitaRepository.save(createEntity(createReceitaDto));
        return new CreatedReceitaDto(
                receitaCreated.getId(),
                receitaCreated.getNome(),
                receitaCreated.getGramas(),
                receitaCreated.getCalorias(),
                receitaCreated.getProteinas(),
                receitaCreated.getCarboidratos(),
                receitaCreated.getGordura(),
                receitaCreated.getTipoRefeicao(),
                receitaCreated.getDescricao(),
                receitaCreated.getDhCriacao()
        );
    }

    public RespostaCalculoReceitaDto calcularReceita(CalculoReceitaDto calculoReceitaDto) {
        String prompt = gerarPromptParaGPT(calculoReceitaDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("sua-chave-de-api");

        String body = """
            {
                "model": "gpt-4",
                "messages": [{"role": "user", "content": "%s"}],
                "max_tokens": 1000
            }
            """.formatted(prompt);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<RespostaCalculoReceitaDto> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                RespostaCalculoReceitaDto.class
        );

        return response.getBody();
    }

    private Receita createEntity(CreateReceitaDto createReceitaDto) {
        Entidade entidade = entidadeRepository.findById(authenticationFacade.getAuthentication().getEntidade())
                .orElseThrow(() -> new RuntimeException("Entidade não encontrada"));

        Usuario usuario = usuarioRepository.findById(authenticationFacade.getAuthentication().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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

        // melhoras prompit (inicial
    private String gerarPromptParaGPT(CalculoReceitaDto calculoReceitaDto) {
        StringBuilder prompt = new StringBuilder();

        if (calculoReceitaDto.isGerarModoPreparo()) {
            prompt.append("Forneça os seguintes dados no formato JSON: ")
                    .append("macronutrientes (proteínas, gorduras, carboidratos, calorias), modo de preparo e gramas por porção ")
                    .append("com base nos ingredientes fornecidos.\nIngredientes:\n");
        } else {
            prompt.append("Forneça os macronutrientes (proteínas, gorduras, carboidratos, calorias) ")
                    .append("no formato JSON com base nos ingredientes, modo de preparo e gramas por porção fornecidos.\nIngredientes:\n");
        }

        calculoReceitaDto.getIngredientes().forEach(ingrediente -> {
            prompt.append(ingrediente.getNome())
                    .append(": ")
                    .append(ingrediente.getQuantidade())
                    .append(" ")
                    .append(ingrediente.getUnidade())
                    .append("\n");
        });

        if (!calculoReceitaDto.isGerarModoPreparo()) {
            prompt.append("\nModo de preparo: ").append(calculoReceitaDto.getModoPreparo());
            prompt.append("\nGramas por porção: ").append(calculoReceitaDto.getGramasPorPorcao());
        }

        return prompt.toString();
    }
}
