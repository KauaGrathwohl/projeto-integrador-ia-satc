package com.nutrisys.api.planometa;

import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import com.nutrisys.api.planometa.dto.ListPlanoMetaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-meta")
public class PlanoMetaController {

    @Autowired
    private PlanoMetaService planoMetaService;

    @PostMapping(value = "/{idPaciente}")
    public CreatedPlanoMetaDto createPlanoMeta(@RequestBody CreatePlanoMetaDto createPlanoMetaDto, @PathVariable("idPaciente") Long idPaciente) {
        try {
            return planoMetaService.createPlanoMeta(createPlanoMetaDto, idPaciente);
        } catch (Exception e) {
            throw new BadRequestException("Erro ao criar plano meta: " + e.getMessage());
        }
    }

    @GetMapping
    public List<ListPlanoMetaDto> listPlanosMeta() {
        try {
            return planoMetaService.listPlanosMeta();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao listar planos meta: " + e.getMessage());
        }
    }
}