package com.nutrisys.api.planometa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrisys.api.model.*;
import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import com.nutrisys.api.planometa.dto.PlanoGeradoDto;
import com.nutrisys.api.repository.*;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.utils.TikTokensUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nutrisys.api.enums.TipoRefeicao;

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
    private PacienteRepository pacienteRepository;

    @Autowired
    private PlanoMetaRefeicaoRepository planoMetaRefeicaoRepository;

    @Autowired
    private PlanoMetaRefeicaoReceitaRepository planoMetaRefeicaoReceitaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${application.gpt-secret}")
    private String gptSecret;

    public CreatedPlanoMetaDto createPlanoMeta(CreatePlanoMetaDto createPlanoMetaDto, Long idPaciente) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);
        if (pacienteOptional.isEmpty()) {
            throw new RuntimeException("Paciente informado não foi encontrado");
        }

        PlanoMeta planoMeta = createEntity(createPlanoMetaDto, pacienteOptional.get());
        PlanoMeta planoMetaCreated = planoMetaRepository.save(planoMeta);
        return new CreatedPlanoMetaDto(planoMetaCreated.getId(),
                pacienteOptional.get().getId(),
                planoMetaCreated.getNomePlano(),
                planoMetaCreated.getDtInicioMeta(),
                planoMetaCreated.getQtdDiariaCalorias(),
                planoMetaCreated.getQtdDiariaCarboidratos(),
                planoMetaCreated.getQtdDiariaGordura(),
                planoMetaCreated.getQtdDiariaProteinas(),
                planoMetaCreated.getDhCriacao());
    }

    public List<ListPlanoMetaDto> listPlanosMeta(Long idPaciente) {
        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        Long usuario = authenticationFacade.getAuthentication().getIdUsuario();
        return planoMetaRepository.findByEntidadeAndUsuarioAndPaciente(entidade, usuario, idPaciente);
    }

    public void gerarPlanoDetalhado(Long idPlano) {
        PlanoMeta planoMeta = planoMetaRepository.findById(idPlano)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado."));

        List<Receita> receitasDisponiveis = receitaRepository.findAll();
        String payload = montarPayloadParaGPT(planoMeta, receitasDisponiveis);
        String respostaGPT = chamarGPT(payload);
        salvarPlanoGerado(respostaGPT, planoMeta);
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

    private String montarPayloadParaGPT(PlanoMeta planoMeta, List<Receita> receitasDisponiveis) {
        StringBuilder prompt = new StringBuilder("Você é um nutricionista que cria planos alimentares personalizados.\n");
        prompt.append("Baseado no objetivo '")
                .append(planoMeta.getDescricao())
                .append("' e nos seguintes valores diários:\n");
        prompt.append("Calorias: ").append(planoMeta.getQtdDiariaCalorias()).append(" kcal, ");
        prompt.append("Proteínas: ").append(planoMeta.getQtdDiariaProteinas()).append(" g, ");
        prompt.append("Carboidratos: ").append(planoMeta.getQtdDiariaCarboidratos()).append(" g, ");
        prompt.append("Gordura: ").append(planoMeta.getQtdDiariaGordura()).append(" g.\n");
        prompt.append("Crie 4 refeições (Café da manhã, Almoço, Café da tarde, Jantar), cada uma com 4 opções de receitas baseadas nas seguintes receitas disponíveis:\n");

        for (TipoRefeicao tipo : TipoRefeicao.values()) {
            prompt.append(tipo.getDescription()).append(":\n");

            List<Receita> receitasFiltradas = receitaRepository.findByTipoRefeicao(tipo);

            for (Receita receita : receitasFiltradas) {
                prompt.append("- Nome: ").append(receita.getNome())
                        .append(", Calorias: ").append(receita.getCalorias())
                        .append(", Proteínas: ").append(receita.getProteinas())
                        .append(", Carboidratos: ").append(receita.getCarboidratos())
                        .append(", Gordura: ").append(receita.getGordura())
                        .append("\n");
            }
        }

        prompt.append("Certifique-se de ajustar as porções para atender às metas de macros por refeição.");
        return prompt.toString();
    }

    private String chamarGPT(String payload) {
        try {
            OpenAiService service = new OpenAiService(gptSecret);
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), payload);

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(TikTokensUtil.ModelEnum.GPT_3_5_TURBO.getName())
                    .messages(List.of(chatMessage))
                    .build();

            return service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar o GPT: " + e.getMessage());
        }
    }

    private void salvarPlanoGerado(String respostaGPT, PlanoMeta planoMeta) {
        try {
            PlanoGeradoDto planoGerado = objectMapper.readValue(respostaGPT, PlanoGeradoDto.class);

            planoGerado.getRefeicoes().forEach(refeicao -> {
                PlanoMetaRefeicao planoMetaRefeicao = planoMetaRefeicaoRepository.save(
                        PlanoMetaRefeicao.builder()
                                .planoMeta(planoMeta)
                                .qtdCaloriasRefeicao(refeicao.getCalorias())
                                .qtdProteinasRefeicao(refeicao.getProteinas())
                                .qtdCarboidratosRefeicao(refeicao.getCarboidratos())
                                .qtdGorduraRefeicao(refeicao.getGordura())
                                .tipoRefeicao(refeicao.getTipoRefeicao())
                                .build()
                );

                refeicao.getReceitas().forEach(receita -> {
                    planoMetaRefeicaoReceitaRepository.save(
                            PlanoMetaRefeicaoReceita.builder()
                                    .planoMetaRefeicao(planoMetaRefeicao)
                                    .descricao(receita.getDescricao())
                                    .qtdCaloriasReceita(receita.getCalorias())
                                    .qtdProteinasReceita(receita.getProteinas())
                                    .qtdCarboidratosReceita(receita.getCarboidratos())
                                    .qtdGorduraReceita(receita.getGordura())
                                    .qtdGramasReceita(receita.getGramas())
                                    .build()
                    );
                });
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar a resposta do GPT: " + e.getMessage());
        }
    }
}
