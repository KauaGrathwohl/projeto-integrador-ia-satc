package com.nutrisys.api.paciente;

import com.nutrisys.api.paciente.dto.CreatePacienteDto;
import com.nutrisys.api.paciente.dto.CreatedPacienteDto;
import com.nutrisys.api.paciente.dto.DetailPacienteDto;
import com.nutrisys.api.paciente.dto.ListPacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public CreatedPacienteDto createPaciente(@RequestBody CreatePacienteDto createPacienteDto) {
        return pacienteService.createPaciente(createPacienteDto);
    }

    @GetMapping
    public List<ListPacienteDto> listPacientes() {
        return pacienteService.listPacienteDtos();
    }

    @GetMapping("/detalhes/{idPaciente}")
    public DetailPacienteDto getDetailPaciente(@PathVariable("idPaciente") Long idPaciente) {
        return pacienteService.getDetailPaciente(idPaciente);
    }
}