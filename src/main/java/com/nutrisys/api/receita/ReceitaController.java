package com.nutrisys.api.receita;

import com.nutrisys.api.exception.BadRequestException;
import com.nutrisys.api.exception.ResourceNotFoundException;
import com.nutrisys.api.receita.dto.CalculoReceitaDto;
import com.nutrisys.api.receita.dto.RespostaCalculoReceitaDto;
import com.nutrisys.api.receita.dto.CreateReceitaDto;
import com.nutrisys.api.receita.dto.CreatedReceitaDto;
import com.nutrisys.api.receita.dto.ListReceitaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public CreatedReceitaDto createReceita(@RequestBody CreateReceitaDto createReceitaDto) {
        return receitaService.createReceita(createReceitaDto);
    }

    @GetMapping
    public List<ListReceitaDto> listReceitas(@RequestParam("filtro") String filtro) {
        return receitaService.listReceitaDtos(filtro);
    }

    @GetMapping("/quantidade")
    public Long getQuantidadeReceitas() {
        return receitaService.getQuantidadeReceitasPorEntidade();
    }

    @PostMapping("/calcular")
    public RespostaCalculoReceitaDto calcularReceita(@RequestBody CalculoReceitaDto calculoReceitaDto) {
        return receitaService.calcularReceita(calculoReceitaDto);
    }
}
