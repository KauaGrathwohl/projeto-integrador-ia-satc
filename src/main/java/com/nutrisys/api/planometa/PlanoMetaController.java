package com.nutrisys.api.planometa;

import com.nutrisys.api.exception.BadRequestException;
import com.nutrisys.api.planometa.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-meta")
public class PlanoMetaController {

    @Autowired
    private PlanoMetaService planoMetaService;

    @PostMapping("/{idPaciente}")
    public CreatedPlanoMetaDto createPlanoMeta(@RequestBody CreatePlanoMetaDto createPlanoMetaDto,
                                               @PathVariable("idPaciente") Long idPaciente) {
        try {
            return planoMetaService.createPlanoMeta(createPlanoMetaDto, idPaciente);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao criar plano meta: " + e.getMessage());
        }
    }

    @PutMapping
    public UpdatePlanoMetaDto updatePlanoMeta(@RequestBody UpdatePlanoMetaDto updatePlanoMetaDto) {
        try {
            return planoMetaService.updatePlanoMeta(updatePlanoMetaDto);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao atualizar plano meta: " + e.getMessage());
        }
    }

    @GetMapping("/{idPaciente}")
    public List<ListPlanoMetaDto> listPlanosMeta(@PathVariable("idPaciente") Long idPaciente) {
        try {
            return planoMetaService.listPlanosMeta(idPaciente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar planos meta: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/detalhes")
    public FindPlanoMetaDto findPlanoMeta(@PathVariable("id") Long id) {
        try {
            return planoMetaService.findPlanoMeta(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar plano meta: " + e.getMessage());
        }
    }

    @PostMapping("/{idPlano}/gerar")
    public PlanoGeradoDto gerarPlanoDetalhado(@PathVariable("idPlano") Long idPlano) {
        try {
            return planoMetaService.gerarPlanoDetalhado(idPlano);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao gerar plano detalhado: " + e.getMessage());
        }
    }

    @GetMapping("/quantidade")
    public Long getQuantidadePlanosMeta() {
        try {
            return planoMetaService.getQuantidadePlanosMetaPorEntidade();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao contar planos meta: " + e.getMessage());
        }
    }
}
