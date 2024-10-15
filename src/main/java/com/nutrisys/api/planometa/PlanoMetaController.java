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
        return planoMetaService.createPlanoMeta(createPlanoMetaDto, idPaciente);
    }

    @GetMapping
    public List<ListPlanoMetaDto> listPlanosMeta() {
        return planoMetaService.listPlanosMeta();
    }
}