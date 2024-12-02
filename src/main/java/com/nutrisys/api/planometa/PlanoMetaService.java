package com.nutrisys.api.planometa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrisys.api.model.*;
import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import com.nutrisys.api.planometa.dto.PlanoGeradoDto;
import com.nutrisys.api.receita.dto.ListReceitaDto;
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

    public PlanoGeradoDto gerarPlanoDetalhado(Long idPlano) throws JsonProcessingException {
        PlanoMeta planoMeta = planoMetaRepository.findById(idPlano)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado."));

        Long entidade = authenticationFacade.getAuthentication().getEntidade();
        Long usuario = authenticationFacade.getAuthentication().getIdUsuario();
        List<ListReceitaDto> receitas = receitaRepository.findByEntidadeAndUsuarioAndFiltro(entidade, usuario, null);

        String datasetReceitas = objectMapper.writeValueAsString(receitas);
        String payload = montarPayloadParaGPT(planoMeta, datasetReceitas);
        String respostaGPT = chamarGPT(payload);

        PlanoGeradoDto planoGeradoDto = objectMapper.readValue(respostaGPT, PlanoGeradoDto.class);
        return planoGeradoDto;
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

    private String montarPayloadParaGPT(PlanoMeta planoMeta, String datasetReceitas) {
        String initialMessage = "Você é um nutricionista que cria planos alimentares personalizados.\n";
        StringBuilder content = new StringBuilder(initialMessage);

        return content.append("Será fornecido um dataset de receitas pre-existentes em nossa base de dados assim como meta de macronutrientes diários (calorias, carboidratos, gordura e proteinas) para o paciente.\n")
                .append("Baseado nesse dataset de receitas pré existentes e também nas metas diárias do paciente, você deve cruzar os macros de cada receita com a meta diária do paciente para recomendar as receitas que se encaixam de acordo com a sua meta.\n")
                .append("O dataset fornecido possui receitas com a seguinte estrutura:\n")
                .append("{\"id\":0,\"nome\":\"\",\"gramas\":0.0,\"calorias\":0.0,\"proteinas\":0.0,\"carboidratos\":0.0,\"gordura\":0.0,\"tipoRefeicao\":0|1|2|3}\n")
                .append("O campo \"tipoRefeicao\" determina qual o tipo de refeição para dada receita, e deve ser levado em consideração no momento de gerar o plano alimentar.\n")
                .append("O campo \"tipoRefeicao\" do dataset pode ser mapeado da seguinte maneira: (0: cafeDaManha, 1: almoco, 2: cafeDaTarde, 3: jantar).\n")
                .append("O plano alimentar fornecido pelo seu output deve conter a seguinte estrutura JSON:\n")
                .append("{\"caloriasDiarias\":0.0,\"carboidratosDiarios\":0.0,\"gorduraDiaria\":0.0,\"proteinasDiarias\":0.0,\"cafeDaManha\":{\"receitas\":[{\"id\":0,\"nome\":\"\",\"calorias\":0.0,\"carboidratos\":0.0,\"gordura\":0.0,\"proteinas\":0.0}]},\"almoco\":{\"receitas\":[{\"id\":0,\"nome\":\"\",\"calorias\":0.0,\"carboidratos\":0.0,\"gordura\":0.0,\"proteinas\":0.0}]},\"cafeDaTarde\":{\"receitas\":[{\"id\":0,\"nome\":\"\",\"calorias\":0.0,\"carboidratos\":0.0,\"gordura\":0.0,\"proteinas\":0.0}]},\"jantar\":{\"receitas\":[{\"id\":0,\"nome\":\"\",\"calorias\":0.0,\"carboidratos\":0.0,\"gordura\":0.0,\"proteinas\":0.0}]}}\n")
                .append("Dataset:\n")
                .append(datasetReceitas).append("\n")
                .append("Metas diárias do paciente: \n")
                .append("Calorias: ").append(planoMeta.getQtdDiariaCalorias()).append("\n")
                .append("Proteínas: ").append(planoMeta.getQtdDiariaProteinas()).append("\n")
                .append("Carboidratos: ").append(planoMeta.getQtdDiariaCarboidratos()).append("\n")
                .append("Gordura: ").append(planoMeta.getQtdDiariaGordura()).append("\n")
                .append("Crie 4 refeições (Café da manhã, Almoço, Café da tarde, Jantar), cada uma com até 4 opções de receitas baseadas nas receitas do dataset.\n")
                .append("Certifique-se de ajustar as porções para atender às metas de macros por refeição.").toString();
    }

    private String chamarGPT(String payload) {
        try {
            OpenAiService service = new OpenAiService(gptSecret);
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), payload);

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(TikTokensUtil.ModelEnum.GPT_3_5_TURBO.getName())
                    .messages(List.of(chatMessage))
                    .build();
            ChatMessage result = service.createChatCompletion(request).getChoices().get(0).getMessage();
            return result.getContent();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar o GPT: " + e.getMessage());
        }
    }

//    private void salvarPlanoGerado(String respostaGPT, PlanoMeta planoMeta) {
//        try {
//            PlanoGeradoDto planoGerado = objectMapper.readValue(respostaGPT, PlanoGeradoDto.class);
//
//            planoGerado.getRefeicoes().forEach(refeicao -> {
//                PlanoMetaRefeicao planoMetaRefeicao = planoMetaRefeicaoRepository.save(
//                        PlanoMetaRefeicao.builder()
//                                .planoMeta(planoMeta)
//                                .qtdCaloriasRefeicao(refeicao.getCalorias())
//                                .qtdProteinasRefeicao(refeicao.getProteinas())
//                                .qtdCarboidratosRefeicao(refeicao.getCarboidratos())
//                                .qtdGorduraRefeicao(refeicao.getGordura())
//                                .tipoRefeicao(refeicao.getTipoRefeicao())
//                                .build()
//                );
//
//                refeicao.getReceitas().forEach(receita -> {
//                    planoMetaRefeicaoReceitaRepository.save(
//                            PlanoMetaRefeicaoReceita.builder()
//                                    .planoMetaRefeicao(planoMetaRefeicao)
//                                    .descricao(receita.getDescricao())
//                                    .qtdCaloriasReceita(receita.getCalorias())
//                                    .qtdProteinasReceita(receita.getProteinas())
//                                    .qtdCarboidratosReceita(receita.getCarboidratos())
//                                    .qtdGorduraReceita(receita.getGordura())
//                                    .qtdGramasReceita(receita.getGramas())
//                                    .build()
//                    );
//                });
//            });
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao processar a resposta do GPT: " + e.getMessage());
//        }
//    }
}
