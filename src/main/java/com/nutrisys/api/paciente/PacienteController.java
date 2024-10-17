package com.nutrisys.api.paciente;

import com.nutrisys.api.exception.ResourceNotFoundException;
import com.nutrisys.api.paciente.dto.CreatePacienteDto;
import com.nutrisys.api.paciente.dto.CreatedPacienteDto;
import com.nutrisys.api.paciente.dto.DetailPacienteDto;
import com.nutrisys.api.paciente.dto.ListPacienteDto;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public CreatedPacienteDto createPaciente(@RequestBody CreatePacienteDto createPacienteDto) throws BadRequestException {
        try {
            return pacienteService.createPaciente(createPacienteDto);
        } catch (Exception e) {
            throw new BadRequestException("Error ao criar paciente: " + e.getMessage());
        }
    }

    @GetMapping
    public List<ListPacienteDto> listPacientes() {
        try {
            return pacienteService.listPacienteDtos();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao listar pacientes: " + e.getMessage());
        }
    }

    @GetMapping("/detalhes/{idPaciente}")
    public DetailPacienteDto getDetailPaciente(@PathVariable("idPaciente") Long idPaciente) {
        try {
            return pacienteService.getDetailPaciente(idPaciente);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao mostrar detalhes do paciente: " + e.getMessage());
        }
    }
}