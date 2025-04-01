package br.com.passos.med_voll.domain.agendamento;

import br.com.passos.med_voll.domain.medico.Especialidade;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        LocalDateTime data,
        Especialidade especialidade
) {
}
