package com.nutrisys.api.receita;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrisys.api.model.Entidade;
import com.nutrisys.api.model.Receita;
import com.nutrisys.api.model.Usuario;
import com.nutrisys.api.receita.dto.*;
import com.nutrisys.api.repository.EntidadeRepository;
import com.nutrisys.api.repository.ReceitaRepository;
import com.nutrisys.api.repository.UsuarioRepository;
import com.nutrisys.api.security.contextprovider.AuthenticationFacade;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.utils.TikTokensUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
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

    @Value("${application.gpt-secret}")
    private String gptSecret;

    @Autowired
    private ObjectMapper objectMapper;

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

    public RespostaCalculoReceitaDto calcularReceita(CalculoReceitaDto calculoReceitaDto) {
        try {
            OpenAiService service = new OpenAiService(gptSecret);
            StringBuilder content = buildContent(calculoReceitaDto);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setRole(ChatMessageRole.SYSTEM.value());
            chatMessage.setContent(content.toString());
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(TikTokensUtil.ModelEnum.GPT_3_5_TURBO.getName())
                    .messages(List.of(chatMessage))
                    .build();
            ChatMessage result = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            RespostaCalculoReceitaDto respostaCalculoReceitaDto = objectMapper.readValue(result.getContent().replaceAll("[^\\p{Print}]", ""), RespostaCalculoReceitaDto.class);
            return respostaCalculoReceitaDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        // Remove caracteres não imprimíveis e espaços desnecessários
        input = input.replaceAll("[^\\p{Print}]", "");
        // Escapar caracteres JSON
        input = StringEscapeUtils.escapeJson(input);
        // Normalizar caracteres Unicode
        input = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return input.trim(); // Remove espaços extras
    }

    private StringBuilder buildContent(CalculoReceitaDto calculoReceitaDto) {
        String initialMessage = "Você é um nutricionista capaz de me recomendar receitas e calcular macros (Proteínas, Gordura, Carboidratos, Calorias e Gramas por porção) de uma dada receita ou da recomendada.";
        StringBuilder content = new StringBuilder(initialMessage);
        content.append("\n");

        if (calculoReceitaDto.gerarModoPreparo()) {
            content.append("Forneça os seguintes dados na seguinte estrutura JSON: {\"macronutrientes\":{\"proteinas\":0.0,\"gorduras\":0.0,\"carboidratos\":0.0,\"calorias\":0.0},\"modoPreparo\":\"\",\"gramasPorPorcao\":0}\n")
                    .append("macronutrientes (proteínas, gorduras, carboidratos, calorias), modo de preparo (modoPreparo) e gramas por porção (gramasPorPorcao)")
                    .append("com base nos ingredientes fornecidos.")
                    .append("\n")
                    .append("Ingredientes:")
                    .append("\n");
        } else {
            content.append("Forneça os seguintes dados na seguinte estrutura JSON: {\"macronutrientes\":{\"proteinas\":0.0,\"gorduras\":0.0,\"carboidratos\":0.0,\"calorias\":0.0}\n")
                    .append("macronutrientes (proteínas, gorduras, carboidratos, calorias) com base nos ingredientes, modo de preparo e gramas por porção fornecidos.")
                    .append("\n")
                    .append("Ingredientes:")
                    .append("\n");
            content.append("Modo de preparo: ").append(calculoReceitaDto.modoPreparo()).append("\n");
            content.append("Gramas por porção: ").append(calculoReceitaDto.gramasPorPorcao()).append("\n");
        }

        calculoReceitaDto.ingredientes().forEach(ingrediente -> {
            content.append("Nome: ")
                    .append(ingrediente.nome()).append(",")
                    .append("Quantidade: ")
                    .append(ingrediente.quantidade()).append(",")
                    .append("Unidade: ")
                    .append(ingrediente.unidade()).append(".")
                    .append("\n");
        });
        return content;
    }

//    public RespostaCalculoReceitaDto calcularReceita(CalculoReceitaDto calculoReceitaDto) {
//        String prompt = gerarPromptParaGPT(calculoReceitaDto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth("sua-chave-de-api");
//
//        String body = """
//            {
//                "model": "gpt-4",
//                "messages": [{"role": "user", "content": "%s"}],
//                "max_tokens": 1000
//            }
//            """.formatted(prompt);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<RespostaCalculoReceitaDto> response = restTemplate.exchange(
//                "https://api.openai.com/v1/chat/completions",
//                HttpMethod.POST,
//                requestEntity,
//                RespostaCalculoReceitaDto.class
//        );
//
//        return response.getBody();
//    }

//    private String gerarPromptParaGPT(CalculoReceitaDto calculoReceitaDto) {
//        StringBuilder prompt = new StringBuilder();
//
//        if (calculoReceitaDto.isGerarModoPreparo()) {
//            prompt.append("Forneça os seguintes dados no formato JSON: ")
//                    .append("macronutrientes (proteínas, gorduras, carboidratos, calorias), modo de preparo e gramas por porção ")
//                    .append("com base nos ingredientes fornecidos.\nIngredientes:\n");
//        } else {
//            prompt.append("Forneça os macronutrientes (proteínas, gorduras, carboidratos, calorias) ")
//                    .append("no formato JSON com base nos ingredientes, modo de preparo e gramas por porção fornecidos.\nIngredientes:\n");
//        }
//
//        calculoReceitaDto.getIngredientes().forEach(ingrediente -> {
//            prompt.append(ingrediente.getNome())
//                    .append(": ")
//                    .append(ingrediente.getQuantidade())
//                    .append(" ")
//                    .append(ingrediente.getUnidade())
//                    .append("\n");
//        });
//
//        if (!calculoReceitaDto.isGerarModoPreparo()) {
//            prompt.append("\nModo de preparo: ").append(calculoReceitaDto.getModoPreparo());
//            prompt.append("\nGramas por porção: ").append(calculoReceitaDto.getGramasPorPorcao());
//        }
//
//        return prompt.toString();
//    }

}