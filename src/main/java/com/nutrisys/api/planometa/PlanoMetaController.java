package com.nutrisys.api.planometa;

import com.nutrisys.api.planometa.dto.CreatePlanoMetaDto;
import com.nutrisys.api.planometa.dto.CreatedPlanoMetaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plano-meta")
public class PlanoMetaController {

    @Autowired
    private PlanoMetaService planoMetaService;

    @PostMapping
    public CreatedPlanoMetaDto createPlanoMeta(@RequestBody CreatePlanoMetaDto createPlanoMetaDto) {
        return planoMetaService.createPlanoMeta(createPlanoMetaDto);
    }

}